# Trend Sight

A new and upcoming app thats broke college students decided to make for authors to write about current events occuring

## Front End
- Must be on the directory TrendSight/Frontend(ie. cd into FrontEnd Folder before running npm commands)
### To run mock database on local host:
```
npm run server
```

### To run application on local host:
```
npm run dev
```

### To preview code before shipping to production
```
npm run preview
```

### To ship/run for production
```
npm run build
```

## To run PostgreSQL/ Local Backend Server 
<!-- In the directory TrendSight/Backend(ie. cd into Backend Folder before running Springboot Application) -->

Before you are able to run Local Backend Server,

You need to have Docker Desktop/Docker installed on local machine to run the PostgreSQL Database to save/load any articles using PostgreSQL. 
You also need to have Maven installed in not already done so.

- To Run SpringBoot Application w/o Terminal => Must open IDE in the TrendSight/Backend Folder then follow the step below

Click arrow on top right of _Application.java_ or any Java file and click on _Application.java_
![Run Springboot App + PostgreSQL DB(using Docker)](/images/SpringbootRun.png)

- Running Springboot with Terminal and Maven => IDE can be opened in any Folder(ie. /TrendSight) but must current Path must be on /TrendSight/Backend => then follow step below

Type this command in your TrendSight/Backend Directory:
` ./mvnw spring-boot:run `

