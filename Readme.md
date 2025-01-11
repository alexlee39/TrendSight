# Trend Sight

A new and upcoming app thats broke college students decided to make for authors to write about current events occuring
## FrontEnd 
- To use these commands below, please redirect into the directory TrendSight/FrontEnd

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

## Backend 
- The Backend uses PostgreSQL Database hosted through Docker and Springboot as the main Backend Language/Framework 

To run the backend server you must have Docker/Docker Desktop installed and Maven installed 

- To run Springboot Application w/o terminal(not recommended, but does refresh automatically if you update backend files)
1) Need to open IDE with the workspace Backend(ie. you can't have your IDE be in opened in TrendSight, it must be opened in TrendSight/Backend so you can only see Backend Files in your IDE)
2) You should select/click any Javafile(ie. Application.java) in the src/main folder. 
3) In VSCode/IntelliJ, you should click Top Right arrow like how you would run a normal Java File.

- Alternatively, can run application in terminal
1) Be in the TrendSight/Backend folder or directory
2) Run ``` ./mvnw spring-boot:run```