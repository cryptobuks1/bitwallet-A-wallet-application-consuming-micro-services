#Overview

The api-gateway application acts the router and authentication and authorization endpoint. 

The Zuul api gateway solves a very common use case where a UI application wants to proxy calls to one or more back end services. This feature is useful for a user interface to proxy to the backend services it requires, avoiding the need to manage CORS and authentication concerns independently for all the backends.
For example in our application `/transaction/**` endpoint is mapped to the `transaction-service`. 

It also knows how to invoke the authorization server in case the user is not authenticated. Once the authentication is complete, it relays the OAuth2 token to the respective services so that they can find the authenticated user and provide services.

##Pre-requisites

### Projects that need to be started before
* [config-service] - For pulling the configuration information
* [registry-service] - For starting the Eureka server    

### Running the application
* To start gateway-service, run the java class 'com.wallet.gateway.Application.java'. 
* On startup, it will get the configuration details from 'config-service.yml'  