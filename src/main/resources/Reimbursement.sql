-------Functions and Statements for Debug:
--select * from validateUser('admin', 'admin');
--select * from ers_reimbursement;
--select * from getReimbursement();
--call updatereimbursementstatus(2,2 ,1);
--insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id) values (20, now(), 2, 1, 1);
--select * from ers_users eu;
--select * from ers_users;

drop table if exists ers_reimbursement_status cascade;
drop table if exists ers_reimbursement_type cascade;
drop table if exists ers_user_roles cascade;
drop table if exists ers_users cascade;
drop table if exists ers_reimbursement cascade;

create table ers_reimbursement_status (
	reimb_status_id serial primary key,
	reimb_status varchar(10) not null unique
);

insert into ers_reimbursement_status (reimb_status) values
	('Pending'),
	('Approved'),
	('Denied');

create table ers_reimbursement_type (
	reimb_type_id serial primary key,
	reimb_type varchar(25) not null unique
);

insert into ers_reimbursement_type (reimb_type) values
	('Lodging'),
	('Travel'),
	('Food'),
	('After School Horde'),
	('Ingredients'),
	('Food Festival'),
	('Other');
	
create table ers_user_roles (
	ers_user_role_id serial primary key,
	user_role varchar(10) not null unique
);

insert into ers_user_roles (user_role) values
	('Employee'),
	('Manager');
	
create table ers_users (
	ers_users_id serial primary key,
	ers_username varchar(50) not null unique,
	ers_password varchar(50) not null,
	user_first_name varchar(100) not null,
	user_last_name varchar(100) not null,
	user_email varchar(150) not null unique,
	user_role_id int references ers_user_roles(ers_user_role_id) not null
);

create table ers_reimbursement (
	reimb_id serial primary key,
	reimb_amount decimal(20, 2) not null,
	reimb_submitted timestamp not null default now(),
	reimb_resolved timestamp,
	reimb_description varchar(250),
--	reimb_receipt bytea,
	reimb_author int references ers_users(ers_users_id) not null,
	reimb_resolver int references ers_users(ers_users_id),
	reimb_status_id int references ers_reimbursement_status(reimb_status_id) not null,
	reimb_type_id int references ers_reimbursement_type(reimb_type_id) not null
);

---------------------function to hash passwords and to work with trigger
-------change the salt keyword for your own project
-------make sure all columns match your table
create or replace function hashPassword()
	returns trigger as $$
		begin
			if(new.ers_password=old.ers_password)then
			return new;
		end if;
		new.ers_password := md5(new.ers_username||new.ers_password||'saltmachine');
		return new;
		end;
	$$ language plpgsql;
----------create the trigger that will call the function when needed
create trigger hashPass
	before insert or update on ers_users
	for each row
	execute function hashPassword();

create or replace function usernameAvailability(u varchar(50))
	returns boolean as $$
		begin 
			if ((select count(ers_username) from ers_users where ers_username = u ) > 0) then 
				return false;
			else
				return true;
			end if;
		end;
	$$ language plpgsql;
	
insert into ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) values
	('admin', 'admin', 'firstadmin', 'lastname', 'adminemail@email', 2),
	('test', 'test', 'firstname', 'lastname', 'email@email', 1);
	
drop function if exists validateUser;

create or replace function validateUser(u varchar(50), p varchar(50))
	returns table(ers_users_id int, 
					ers_username varchar(50),
					ers_password varchar(50),
					user_first_name varchar(100), 
					user_last_name varchar(100), 
					user_email varchar(150), 
					user_role_id int) as $$
		begin
			return query select eu.ers_users_id,
								eu.ers_username,
								eu.ers_password,
								eu.user_first_name, 
								eu.user_last_name, 
								eu.user_email, 
								eu.user_role_id
						from ers_users eu 
						where eu.ers_username = u and eu.ers_password = hashPassword(u, p);
		end
	$$ language plpgsql;

create or replace function hashPassword(u varchar(50), p varchar(50))
	returns text as $$
		begin
			return md5(u || p || 'saltmachine');
		end;
	$$ language plpgsql;

drop procedure if exists updateReimbusementStatus(int, int, int ,int);

create or replace procedure updateReimbursementStatus(id int, status int, resolver int, inout query int default null)
	language plpgsql as $$
		begin
			update ers_reimbursement 
				set reimb_status_id = status,
					reimb_resolver = resolver,
					reimb_resolved = now()
				where reimb_id = id returning 1 into query;
			
			commit;
		end;
	$$;


/*select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, rs.reimb_status_id, reimb_status, ert.reimb_type_id, reimb_type,
		author_id, author_username, author_password, author_first_name, author_last_name, author_email, author_role_id, author_role,
		resolver_id, resolver_username, resolver_password, resolver_first_name, resolver_last_name, resolver_email, resolver_role_id, resolver_role from ers_reimbursement ers 
	left join ers_reimbursement_status rs on ers.reimb_status_id = rs.reimb_status_id
	left join ers_reimbursement_type ert on ers.reimb_type_id = ert.reimb_type_id
	left join 
		(select eu.ers_users_id as author_id, eu.ers_username as author_username, eu.ers_password as author_password,
			eu.user_first_name as author_first_name, eu.user_last_name as author_last_name, eu.user_email as author_email, eu.user_role_id as author_role_id, eur.user_role as author_role 
			from ers_users eu left join ers_user_roles eur on eu.user_role_id = eur.ers_user_role_id) as author on author.author_id = ers.reimb_author 
	left join
		(select eu.ers_users_id as resolver_id, eu.ers_username as resolver_username, eu.ers_password as resolver_password,
			eu.user_first_name as resolver_first_name, eu.user_last_name as resolver_last_name, eu.user_email as resolver_email, eu.user_role_id as resolver_role_id, eur.user_role as resolver_role 
			from ers_users eu left join ers_user_roles eur on eu.user_role_id = eur.ers_user_role_id) as resolver on resolver.resolver_id = ers.reimb_resolver; */
		
-- Let's not force the jdbc to make a million calls to the DB, instead, make one stupid call
create or replace function getReimbursement()
	returns table(reimb_id int, 
		reimb_amount decimal(20, 2), 
		reimb_submitted timestamp, 
		reimb_resolved timestamp, 
		reimb_description varchar(250), 
		reimb_status_id int, 
		reimb_status varchar(10), 
		reimb_type_id int, 
		reimb_type varchar(10),
		author_id int, 
		author_username varchar(50), 
		author_password varchar(50), 
		author_first_name varchar(100), 
		author_last_name varchar(100), 
		author_email varchar(150), 
		author_role_id int, 
		author_role varchar(10),
		resolver_id int, 
		resolver_username varchar(50), 
		resolver_password varchar(50), 
		resolver_first_name varchar(100), 
		resolver_last_name varchar(100), 
		resolver_email varchar(150), 
		resolver_role_id int, 
		resolver_role varchar(10)) as $$
		begin
			return query select ers.reimb_id, ers.reimb_amount, ers.reimb_submitted, ers.reimb_resolved, ers.reimb_description, rs.reimb_status_id, rs.reimb_status, ert.reimb_type_id, ert.reimb_type,
						au.author_id, au.author_username, au.author_password, au.author_first_name, au.author_last_name, au.author_email, au.author_role_id, au.author_role,
						re.resolver_id, re.resolver_username, re.resolver_password, re.resolver_first_name, re.resolver_last_name, re.resolver_email, re.resolver_role_id, re.resolver_role from ers_reimbursement ers 
					left join ers_reimbursement_status rs on ers.reimb_status_id = rs.reimb_status_id
					left join ers_reimbursement_type ert on ers.reimb_type_id = ert.reimb_type_id
					left join 
						(select eu.ers_users_id as author_id, eu.ers_username as author_username, eu.ers_password as author_password,
							eu.user_first_name as author_first_name, eu.user_last_name as author_last_name, eu.user_email as author_email, eu.user_role_id as author_role_id, eur.user_role as author_role 
							from ers_users eu left join ers_user_roles eur on eu.user_role_id = eur.ers_user_role_id) as au on au.author_id = ers.reimb_author 
					left join
						(select eu.ers_users_id as resolver_id, eu.ers_username as resolver_username, eu.ers_password as resolver_password,
						eu.user_first_name as resolver_first_name, eu.user_last_name as resolver_last_name, eu.user_email as resolver_email, eu.user_role_id as resolver_role_id, eur.user_role as resolver_role 
						from ers_users eu left join ers_user_roles eur on eu.user_role_id = eur.ers_user_role_id) as re on re.resolver_id = ers.reimb_resolver;
		end
	$$ language plpgsql;

insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id) values (20, now(), 2, 1, 1);