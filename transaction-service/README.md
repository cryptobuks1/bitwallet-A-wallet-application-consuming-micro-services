#Overview

This application provides the **transaction** related functionality and serves as one component. It defines the REST endpoints that are used to provide transaction functionality. Note that this component is also used internally by the "account-service" microservice.

##Pre-requisites

### Projects that need to be started beforehand
* [config-service] - For pulling the configuration information from a location on cloud. In our case (and as recommended) Github.
* [auth-service] - For starting the Eureka server since the authorization server also is a micro-service that needs to be registered with Eureka server.  
  
### Data-source that need to be setup beforehand
* Database used is MySQL server 5.5
* Run the sql scripts located at '<app-root>/wallet-db/', if not already while starting the account-service.

## Running the application
* As each of the services exist as microservice, we can run each of them independently on different ports (even on different 
  machines/platform). However in the simplest approach of running all services locally, to start auth-service, run the java class 'com.wallet.transaction.Application.java'. 
* On startup, it will get the configuration details from 'transaction-service.yml'  


