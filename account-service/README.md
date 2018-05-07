#Overview

This application provides the user's **account** related functionality and serves as one component. It defines the REST endpoints that are used to provide account functionality.

This micro-service also provides an example of to call another OAuth2 protected service from within this service using OAuth2 client configuration. The OAuth2 bearer token that has been passed to the account-service is propagated to the transaction-service to get the transactions for the given account.


##Pre-requisites

### Projects that need to be started beforehand
* [config-service] - For pulling the configuration information from a location on cloud. In our case (and as recommended) Github.
* [auth-service] - For starting the Eureka server since the authorization server also is a micro-service that needs to be registered with Eureka server.  
  
### Data-source that need to be setup beforehand
* Database used is MySQL server 5.5
* The persistance mechanism used will automatically create the required tables for auth-service to start.

## Running the application
* As each of the services exist as microservice, we can run each of them independently on different ports (even on different 
  machines/platform). However in the simplest approach of running all services locally, to start auth-service, run the java class 'com.wallet.authentication.Application.java'. 
* On startup, it will get the configuration details from 'config-service'  

