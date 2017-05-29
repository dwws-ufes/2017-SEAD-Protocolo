# 2017-SEAD-Protocolo
Assignment for the 2017 edition of the "Web Development and the Semantic Web" course, by Gustavo Epichim Monjardim and Wagner de Andrade Perin

## About this project
SeadProtocolo is a system for call management used in SEAD / UFES.

### Prerequisites
The prerequisites and tutorial for installation all the environment to run the project https://github.com/dwws-ufes/jbutler/wiki/Tutorial%3A-a-Java-EE-Web-Profile-application-with-JButler%2C-part-1

## How to deploy
1. Install [Eclipse jee Neon 3](http://www.eclipse.org/home/index.php); 

2. Install [WildFly 10](http://wildfly.org) and create a Server configuration within Eclipse;
  2.1. Remember to install JBoss's plugin for proper use of Wildfly Application Servers (follow the link in the "Note" for more details).

3. Install [MySQL](http://www.mysql.com/products/community/) and create a schema called `seadprotocolo` and a user called `seadprotocolo` with password `seadprotocolo` and full access to the homonymous database;

4. Configure [the MySQL JDBC driver](http://dev.mysql.com/downloads/connector/j/) in WildFly;

5. Configure the datasource in WildFly's `standalone.xml` file:

```XML
<datasource jta="true" jndi-name="java:jboss/datasources/SeadProtocolo" pool-name="SeadProtocoloPool" enabled="true" use-java-context="true">
    <connection-url>jdbc:mysql://localhost:3306/seadprotocol</connection-url>
    <driver>mysql</driver>
    <security>
        <user-name>seadprotocolo</user-name>
        <password>seadprotocolo</password>
    </security>
</datasource>
``` 

Note: if you need detailed instructions on how to set up Eclipse, WildFly and MySQL, please refer to this [tutorial at nemo-utils wiki](https://github.com/nemo-ufes/nemo-utils/wiki/Tutorial%3A-a-Java-EE-Web-Profile-application-with-nemo-utils%2C-part-1).

## Configuring the project inside Eclipse

Usually, the project misses some dependencies when imported into Eclipse. Checking the following step should be enough for everything to work:

1.  Inside the project's properties, set Wildfly 10 as Target Runtime

2.  Into the Build Path, add the libraries for:
    - JavaSE - 1.8
    - Maven Dependencies
    - Server Runtime

3.  Into the projects Facets, disable and re-enable JSF and JPA facets checking the "Further Configurations" option to use the libraries provided by the target runtime.

## Authors

* **Wagner de Andrade Perin and Gustavo Epichim Monjardim**
