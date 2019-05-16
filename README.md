Course Project
==============
[![Build Status](https://travis-ci.org/Brest-Java-Course-2019/Maksim-Martsiusheu.svg?branch=master)](https://travis-ci.org/Brest-Java-Course-2019/Maksim-Martsiusheu)
[![Coverage Status](https://coveralls.io/repos/github/Brest-Java-Course-2019/Maksim-Martsiusheu/badge.svg?branch=master)](https://coveralls.io/github/Brest-Java-Course-2019/Maksim-Martsiusheu?branch=master) 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
```
install git
install tomcat
install maven 3+
install openjdk 8
```

### Installing

Choose directory in which you want download project. After this, download project from github:

```
$ git clone https://github.com/Brest-Java-Course-2019/Maksim-Martsiusheu
```
### Build project
For build project you can use next commands: 
```
$ cd /Maksim-Martsiusheu/CourseProject/
$ mvn clean install
```

### Preparing reports
For preparing reports of project use:
````
$ mvn site
````
And open:
````
/Maksim-Martsiusheu/CourseProject/target/site/index.html
````
## Deploying application on Tomcat server

* In the *settings.xml* file ($M2_HOME/conf/settings.xml) write the settings for access to the server. Example:
```
 <server>
     <id>TomcatServer</id>
     <username>admin</username>
     <password>admin</password>
 </server>
```
> *Node*: server identifier in the settings.xml file must match the identifier in pom.xml in the root of the plugin project "tomcat7-maven-plugin"
* When the settings are completed, run the following commands:
````
$ cd /Maksim-Martsiusheu/CourseProject/rest-app/
$ mvn tomcat7:deploy

$ cd /Maksim-Martsiusheu/CourseProject/web-app/
$ mvn tomcat7:deploy
````
After this you check web-application on http://localhost:8080/web
Or rest-application on  http://localhost:8080/rest


## Author

**Maksim Martsiusheu** - [MaksimMartsiusheu](https://github.com/MaksimMartsiusheu)
## License

This project is licensed under the MIT License

