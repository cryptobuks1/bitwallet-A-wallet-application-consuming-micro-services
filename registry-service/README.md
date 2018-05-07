#Overview

This application provides the **Eureka Server** that provides service discovery and enables all Eureka clients to discover each other.

When a client registers with Eureka, it provides meta-data about itself such as host and port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance is normally removed from the registry.

##Pre-requisites

### Projects that need to be started before
* [config-service] - For pulling the configuration information from a location on cloud. In our case (and as recommended) Github.

### Running the application
* To start registry-service, run the java class 'com.wallet.hystrixdashboard.Application.java'. 
* On startup, it will get the configuration details from 'registry-service.yml'  