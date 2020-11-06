# Expense-Reimbursement-System
[Example Site](http://ec2-18-221-114-146.us-east-2.compute.amazonaws.com:8080/Reimbursement)
## Project Description

Here goes your awesome project description!

## Technologies Used

* Java - JRE 1.8
* PostgreSQL 10
* HTML5 & CSS3
* JavaScript
* Fetch API
* Bootstrap 4.5
* Apache Tomcat
* JDBC
* AWS EC2
* AWS RDS
* Jenkins

## Features

* Employees/Finance Managers can open Reimbursement Claims for a variety of types, along with a description and claim amount.
* Finance Managers can view all claims, sorted by default from latest to oldest.
* Finance Managers can filter claims by type and/or status.
* Finance Managers can approve/deny claims, and instantly see updated claims.
* Employees can view their own claims, updated, and are able to filter them as well.
* Finance Managers can create new users as either Employees or Finance Managers.
* Employees/Finance Managers can update their own personal information.

To-do list:
* Make claim approval/denial smoother by dynamically generating approve/deny buttons next to each pending claim.
* Separate Claims and User info into different pages

## Getting Started
   
This project was designed to operate on an AWS EC2 instance with Apache Tomcat and Jenkins as part of CI/CD. However, it can also run locally. To download and run a local copy of this project, start by git cloning this repository to your preferred directory.
```
git clone https://github.com/Alteras1/Expense-Reimbursement-System.git
```
Inside `src/main/resources` contains a PostgreSQL script that will set up the database. Please run it in your database instance. To connect your code to the database, please edit the relevant information listed in the `pom.xml`'s `systemPropertyVariables` within the `maven-surefire-plugin`, as well Java system variables, setting `aws_url`, `aws_password`, and `aws_username` to the database url, password, and username.

Once the database is properly set up, you can run the program through a Apache Tomcat server, where it will be hosted under the endpoint `/Reimbursement`.

## Usage

> Here, you instruct other people on how to use your project after they’ve installed it. This would also be a good place to include screenshots of your project in action.

## License

This project uses the following license: [<license_name>](<link>).
