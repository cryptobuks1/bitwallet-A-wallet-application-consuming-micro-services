#Overview

This application loads and makes the **external configurations** available to rest of the applications. The external configuration can be hosted either on GitHub or on local file system.

The respective applications pick up the configuration based on a `<spring.application.name>.yml` file defined in the configuration by matching the service's `spring.application.name` property defined in the `bootstrap.yml` file.

##Pre-requisites

### Projects that need to be started before
* This is the first application that needs to run since it pulls the configuration information(like what port to run etc) that is needed by rest of the applications to start.

### Testing the config-service application
* Start the application and request for eg. 'http://localhost:8888/auth-service.yml' from the browser.
* Update the .yml file and make the request again. You will get the updated .yml file without reloading the servers. 
