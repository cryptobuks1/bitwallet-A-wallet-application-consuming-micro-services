#Overview

This is a Single Page Application(SPA) that has the user facing UI being used in the application. It uses the following technologies
* Angular - (base, routes)
* Bootstrap for CSS and layout
* Gradle for build - we could have used grunt/gulp instead

It is perfectly acceptable to have the SPA not start using the Spring boot application. You would only need to change the Zuul route to forward the request to the actual SPA hosted URL.


##Pre-requisites

### Projects that need to be started before
* [config-service] - For pulling the configuration information
* [registry-service] - For starting the Eureka server    

### Running the application
* To start gateway-service, run the java class 'com.wallet.hystrixdashboard.Application.java'. 
* On startup, it will get the configuration details from 'hystrix-dashboard.yml'  