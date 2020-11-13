# Expense-Reimbursement-System

This is the Expense Reimbursement System project, made by me, Steven Chang (Alteras1). The goal is to create a full stack website and backend that will take in reimbursement claims made by a user, which can be approved by a manager, and subsequently be updated in both the user view and in the database. The premise of this site is that it is a site for a Mochi Donut Bakery.

Here is a link to a [live site](http://18.222.229.144:8080/Reimbursement/), where you can view it in action. By default, there is a manager user with the credentials `johnsamson` and `samsonite`, and an user with the credentials `emilylee` and `leelee`.

## Technologies Used

- Java - JRE 1.8
- PostgreSQL 10
- HTML5 & CSS3
- JavaScript
- Fetch API
- Bootstrap 4.5
- Apache Tomcat
- JDBC
- AWS EC2
- AWS RDS
- Jenkins

## Features

- Employees/Finance Managers can open Reimbursement Claims for a variety of types, along with a description and claim amount.
- Finance Managers can view all claims, sorted by default from latest to oldest.
- Finance Managers can filter claims by type and/or status.
- Finance Managers can approve/deny claims, and instantly see updated claims.
- Employees can view their own claims, updated, and are able to filter them as well.
- Finance Managers can create new users as either Employees or Finance Managers.
- Employees/Finance Managers can update their own personal information.

To-do list:

- Make claim approval/denial smoother by dynamically generating approve/deny buttons next to each pending claim.
- Separate Claims and User info into different pages

## Getting Started

This project was designed to operate on an AWS EC2 instance with Apache Tomcat and Jenkins as part of CI/CD. However, it can also run locally. To download and run a local copy of this project, start by git cloning this repository to your preferred directory.

```
git clone https://github.com/Alteras1/Expense-Reimbursement-System.git
```

Inside `src/main/resources` contains a PostgreSQL script that will set up the database. Please run it in your database instance. To connect your code to the database, please edit the relevant information listed in the `pom.xml`'s `systemPropertyVariables` within the `maven-surefire-plugin`, as well Java system variables, setting `aws_url`, `aws_password`, and `aws_username` to the database url, password, and username.

Once the database is properly set up, you can run the program through a Apache Tomcat server, where it will be hosted under the endpoint `/Reimbursement`.

## Usage

When you first open the page, you'll be greeted with the following Home screen. To login, click on Accounts.

![Imgur](https://i.imgur.com/J0eS8eI.png)

By default, there will be an `admin` `admin` account, and a `test` `test` account, a manager and an employee account respectively, that you can sign into.

![Imgur](https://i.imgur.com/Z3K8pT0.png)

If you log into a manager account, you'll see the following screen. From here, you can view all reimbursements, filter them, and create new accounts. You can also pull up the specific details of a claim by inputting the claim # into the `View Reimbursement Claim` box. From there, you'll be able to update the status of the claim to either approved or denied.

![Imgur](https://i.imgur.com/OqWOCni.png)

If you log in as an employee, you'll see the following screen. Managers are also able to view this and make their own claims by clicking on the dropdown on the top right of the navbar, and clicking on employee view.

Here you can view your own reimbursement claims, their status, open new claims, and update personal information. To update personal information, the password must be given.

![Imgur](https://i.imgur.com/FmPlkMZ.png)

To log out, you can click on the dropdown on the top right of the navbar, and click logout, where it'll execute a log out process for both the frontend and the backend.
