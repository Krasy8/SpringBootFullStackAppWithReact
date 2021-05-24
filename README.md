# Spring Boot Full Stack App

![App Demo](https://j.gifs.com/vlnwZL.gif)

#### A simple full stack application servicing CRUD operations deployed to AWS.

## Table of content

- 🕹 [Play around with my app](#play-around-with-my-app)

- 🏠 [Local install](#local-install)

- 🏗 [Built With](#built-with)  

- 🕵🏻‍♂️ [Tracking with Snowplow](#tracking-with-snowplow)

- ➕ [Contributing](#contributing)

- 🛎 [Get Help](#get-help) 

- ⚡️ [Motivation](#motivation)

- 🙏🏻 [Acknowledgments](#acknowledgements)

## Play around with my app

[My app running on AWS](http://springbootwithreactapp-env.eba-zc8bgb6a.eu-west-2.elasticbeanstalk.com)

Feel free to play around with my app using the link above.  
You will be able to perform all CRUD operations:

**C** - **create** a student using the "Add new student+" button at the bottom of the screen  
**R** - **read** all the information about students added to the list  
**U** - **update** a student using blue "Edit" button on the right-hand side in the column "Action"  
**D** - **delete** a student using red "Delete" button on the right-hand side in the column "Action"  

## Local install  

If you would like to play with my app on your local machine, first you need to make sure you have installed:  

1. Java JDK 11+  -> [[download 📥]](https://www.oracle.com/uk/java/technologies/javase-jdk11-downloads.html)  
2. Maven  -> [[download 📥]](https://maven.apache.org/download.cgi)
3. Docker  -> [[download 📥]](https://www.docker.com/get-started)  
4. Node.js  -> [[download 📥]](https://nodejs.org/en/)
  
  
When your prerequisite are completed, open your terminal and follow the steps below:

1. Clone this repository  

   `git clone https://github.com/Krasy8/SpringBootFullStackAppWithReact.git`  

2. You will need a couple of docker containers (postgres and adminer) running to support the app.  
   Cd into the root folder of the project where you can find the docker-compose.yml file and run  
     
   `docker compose up`  
     
3. Now you can start the app by either running:  
   
   `mvn spring-boot:run`  
   
   or building a .jar file by running:
   
   `mvn install`  
   
   (which should land inside the "**target**" folder and look like this "**fullStackApp-0.0.1-SNAPSHOT.jar**")  
   
   and executing it by running:   
   
   `java -jar target/fullStackApp-0.0.1-SNAPSHOT.jar`  

4. The backend of the app should be running now on port 8080, all there is left to do is to start the front end on port 3000 by:  
   
   cd inside the "js" folder  
   
   `cd src/js`  
   
   and running:  
   
   `npm start`  
   
5. Open http://localhost:3000/ in your browser and you should see the UI. You can start adding students!  
   
   Have fun! 🥳  
   

## Built With
 [![Java Badge](https://img.shields.io/badge/-Java-007396?style=for-the-badge&labelColor=white&logo=java&logoColor=007396)](https://www.java.com/en/) [![Spring Badge](https://img.shields.io/badge/-Spring-6DB33F?style=for-the-badge&labelColor=white&logo=spring&logoColor=6DB33F)](https://spring.io) [![Postgres Badge](https://img.shields.io/badge/-Postgres-336791?style=for-the-badge&labelColor=white&logo=postgresql&logoColor=336791)](https://www.postgresql.org) [![Docker Badge](https://img.shields.io/badge/-Docker-2496ED?style=for-the-badge&labelColor=white&logo=docker&logoColor=2496ED)](https://www.docker.com) [![Javascript Badge](https://img.shields.io/badge/-Javascript-F7DF1E?style=for-the-badge&labelColor=black&logo=javascript&logoColor=F7DF1E)](https://www.javascript.com) [![React Badge](https://img.shields.io/badge/-React-61DAFB?style=for-the-badge&labelColor=black&logo=react&logoColor=61DAFB)](https://reactjs.org) [![AWS Badge](https://img.shields.io/badge/-Amazon_Web_Services-232F3E?style=for-the-badge&labelColor=FF9900&logo=Amazon-AWS&logoColor=232F3E)](https://aws.amazon.com)  
 
 
## Tracking with Snowplow  
  
I have created a separate version of my app where I experimented with Snowplow trackers.  
Please follow the link to a separate branch of this repository called [**'snowplow'**](https://github.com/Krasy8/SpringBootFullStackAppWithReact/tree/snowplow) dedicated to sp trackers implementation.

## Contributing

#### Issues

In the case of a bug report, bugfix or a suggestions, please feel very free to open an issue.

#### Pull request

Pull requests are always welcome, and I'll do my best to do reviews as fast as I can.

## Get Help

- Contact me on krasy8@gmail.com

- If appropriate, [open an issue](https://github.com/Krasy8/SpringBootFullStackAppWithReact/issues) on GitHub

## Motivation

I have built this demo project to showcase my abilities as a software developer. It combines both: front-end and back-end technologies, as well as, packaging the application and deploying it to Amazon Web Services. This was my first full-stack project which gave me as much head scratches 😤 as satisfaction at the end 😎.

## Acknowledgements

[Amigoscode](https://amigoscode.com/p/full-stack-spring-boot-react) - This is my main inspiration and guide for the project. 
A huge thumbs up for Nelson - the founding father of Amigoscode👍.

[Ant Design](https://ant.design) - A UI Design Language and React UI library, which I used for my UI components.  
  
[Snowplow](https://snowplowanalytics.com) - A powerfull and very customisable behavioral data management platform. 
