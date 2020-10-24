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
	('pending'),
	('approved'),
	('denied');

create table ers_reimbursement_type (
	reimb_type_id serial primary key,
	reimb_type varchar(10) not null unique
);

insert into ers_reimbursement_type (reimb_type) values
	('lodging'),
	('travel'),
	('food'),
	('other');
	
create table ers_user_roles (
	ers_user_role_id serial primary key,
	user_role varchar(10) not null unique
);

insert into ers_user_roles (user_role) values
	('employee'),
	('manager');
	
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
	reimb_submitted timestamp not null,
	reimb_resolved timestamp,
	reimb_description varchar(250),
	reimb_receipt bytea,
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
	('admin', 'admin', 'firstadmin', 'lastname', 'adminemail', 2),
	('test', 'test', 'firstname', 'lastname', 'email', 1);
	
drop function if exists validateUser;

create or replace function validateUser(u varchar(50), p varchar(50))
	returns table(ers_users_id int, 
					ers_username varchar(50),
					user_first_name varchar(100), 
					user_last_name varchar(100), 
					user_email varchar(150), 
					user_role_id int) as $$
		begin
			return query select eu.ers_users_id,
								eu.ers_username, 
								eu.user_first_name, 
								eu.user_last_name, 
								eu.user_email, 
								eu.user_role_id
						from ers_users eu 
						where eu.ers_username = u and eu.ers_password = p;
		end
	$$ language plpgsql;

create or replace function hashPassword(u varchar(50), p varchar(50))
	returns text as $$
		begin
			return md5(u || p || 'saltmachine');
		end;
	$$ language plpgsql;
	
insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id) values 
	(20, now(), 3, 1, 1);
	
select * from ers_reimbursement;