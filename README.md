spring-xd-sqoop-job
===================

This is a fork from https://github.com/tzolov/spring-xd-sqoop-job that cleans up hadoop configuration mechanism.

Simple [Sqoop] Job module for [Spring-XD]. Works with Hadopp 2 based Hadoop distributions. 

Sqoop export usage:
	xd:>job create --name <job-name> --definition "sqoop --command='export 
	  	--connect jdbc:mysql://<db-hostname>/<target-db-name>
	  	--table <target-table-name> 
	  	--username <db-username> 
	  	--password <db-password>
	  	--export-dir /hdfs/source/folder/*.csv'"

Example list table usage:
	  	job create testsqoopjob --definition "sqoop list-tables --connect jdbc:postgresql://127.0.0.1:5432/tch --driver org.postgresql.Driver --username tch"


## Build and Installation

Build the job jar:

	mvn clean package

Copy the result `xd-sqoop-module-0.0.1-SNAPSHOT-job.jar` into `${XD_HOME}/modules/job/sqoop/lib`	
	
	cp target/xd-sqoop-module-0.0.1-SNAPSHOT-job.jar `${XD_HOME}/modules/job/sqoop/lib`

Copy the `sqoop.xml` module definition into `${XD_HOME}/modules/job/sqoop/config`	
	
	cp src/main/resources/sqoop.xml ${XD_HOME}/modules/job/sqoop/config
	
Make sure that the hadoop fs uri is configured properly in spring xd servers.yml configuration file:

	# Hadoop properties
	spring:
	  hadoop:
	  fsUri: hdfs://localhost:8020

## Usage

#### Start Spring-XD

Start the admin

	${XD_HOME}/bin/xd-single node --hadoopDistro cdh5

Start xd-shell (in separate shell)

	${XD_HOME}/../shell/bin/xd-shell


#### [Sqoop Export][]
The export tool exports a set of files from HDFS back to an RDBMS. The target table must already exist in the database. The input files are read and parsed into a set of records according to the user-specified delimiters.

The default operation is to transform these into a set of INSERT statements that inject the records into the database. In "update mode," Sqoop will generate UPDATE statements that replace existing records in the database.


Sample data export from HDFS to remote MySQL database:

	xd:>job create --name hdfsToDbExport --definition "sqoop --command='export 
	  --connect jdbc:mysql://your-db-hostname/target-db-name
	  --username db-username --password db-password 
	  --table target-table-name 
	  --export-dir /hdfs/source/folder/*.csv'"
	
	xd:>stream create --name exportTrigger --definition "trigger > job:hdfsToDbExport"
  
  
  
[Sqoop]: http://sqoop.apache.org/docs/1.4.2/SqoopUserGuide.html  
[Sqoop Export]: http://sqoop.apache.org/docs/1.4.2/SqoopUserGuide.html#_literal_sqoop_export_literal
[Spring-XD]: http://projects.spring.io/spring-xd/
[Pivotal HD]: http://www.gopivotal.com/pivotal-products/data/pivotal-hd
