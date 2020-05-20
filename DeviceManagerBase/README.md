# DeviceManagerV2
DeviceManager with Redis caching and proxy-microservice structure for more extensibility

## Tools and dependencies

### Tools

Tools required:
- java
- node
- npm or yarn, whatever you choose
- redis
- maven

Please look up the instructions for installing these tools on your computer.

### Dependencies
Once finished, please navigate to this project in a terminal and then run the following
- If you use yarn: `mvn install ; cd app ; yarn install`
- if you use npm: `mvn install ; cd app ; npm install`

## Run the application

In the future, I will add a script to run everything, but for now, to run this program, you will need to open four terminals
and enter the commands in the order specified:

(Assuming all terminals are in the directory of the project)

Terminal 1: `redis-server`
Terminal 2: `mvn spring-boot:run`
Terminal 3: `cd app/src/server ; node server.js`
Terminal 4: 
- If you use yarn: `cd app ; yarn start`
- if you use npm: `cd app ; npm start`

Afterwards, navigate to localhost:3000 and the program will start.
