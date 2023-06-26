CREATE DATABASE 'employees';
USE employees;

create table employees (
id  int(3) NOT NULL AUTO_INCREMENT,
full_name varchar(120) NOT NULL,
birthday varchar(220),
address varchar(120),
position varchar(220),
department varchar(120),
PRIMARY KEY (id)
);

1. Clone source code here: https://github.com/dainqd/javaEE-example
2. Start xampp and run query:
<p>CREATE DATABASE 'employees';
USE employees;</p>
<p>create table employees (
id  int(3) NOT NULL AUTO_INCREMENT,
full_name varchar(120) NOT NULL,
birthday varchar(220),
address varchar(120),
position varchar(220),
department varchar(120),
PRIMARY KEY (id)
);</p>
3. Open project with your IDE, start project, and follow <a href="http://localhost:8080/baseJavaEE_war_exploded/list">link(http://localhost:8080/{name_project}/list)</a> to start