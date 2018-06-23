# Auth-Server

## Introduction
This service acts as the authetication and authorization service for the ecosystem of micro-services that we developed. 

We use a JWT (Json Web Token) to pass the access token wherein the token itself contains enough information for the resource server to do simple user authentication. The "/me" enpoint still needs to be used by the browser to see if the user is authenticated or not; but is not required in intra-service communication.

## Prerequistes

### Setting Authentication Schema Information
  * The mysql database information is available on the config server GitHub configuration.
  * Please look up the "application.yml" file under the "config-server" project for the location of GitHub repository where the database information has to be updated.
  * At the GitHub repository, update the "auth-service.yml" file section below for the mysql database where the authentication schema would be stored.
     ```
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/oauth
         username: root
         password: password
         driver-class: com.mysql.jdbc.Driver
     ```    
  * At a minumum, you would need to change the JDBC URL to point to where your mysql server is running.  
             			
### Projects that need to be started before
* config service - For pulling the configuration information
* registry-service - For starting Eureka server since the authorization server also is a micro-service that needs to be registered with Eureka server.           	

### Setting up public-private keys for JWT encryption
* Since the JWT token has the user credentials already embedded, we need to ensure that the token is encrypted either using a symmetric/asymmetric key.
* To create your own public-private key pair using java
  * Generate keystore using command as below
  	```
  	keytool -genkey -alias authkey -keyalg RSA -keystore afsharkeystore.jks -keysize 2048
  	```
  * You would need to provide password and the other required information to generate the security certificate.
  * Once the keystore file is generated, we would need to provide the public key to our services so that they can decrypt the JWT token.
    ```
    keytool -list -rfc --keystore afsharkeystore.jks | openssl x509 -inform pem -pubkey
    ```
  * Extract the value in-between `-----BEGIN PUBLIC KEY-----` and `-----END PUBLIC KEY-----` which is the public key.
  * Add public key to the configuration
    * Locate the external configuration repository specified by the config server's [application.yml](..config-server/src/main/resources/application.yml) file.
    * Make changes to the following configuration files to replace the existing public key.
      * api-gateway.yml
      * transaction-service.yml
      * account-service.yml
  * When testing the authentication and authorization flow, ensure that you don't have cookies and HTTP basic credentials stored in the browser cache. The simplest way to do that say in `Chrome` is to open a new `incognito window`.


### Testing different authorization grant types  	
#### Authorization code 
  * This flow is typically used by web server apps(server-to-server communication) to authorize user and then get token from server using POST requests.
  * The user needs to be authenticated (if required), before the request is sent to the authorization server.
  * The authentication credentials are user `dave` and password `secret`. You can add more user if required in the
   `OAuthServerConfiguration` class; refer `AuthenticationManagerConfiguration` inner class for user initialization.
  * After opening an incognito window, paste the following URL(**Note: response_type=code**) in the browser bar
 		```
 		http://localhost:8899/userauth/oauth/authorize?response_type=code&client_id=acme&redirect_uri=http://localhost:8090/index.html
 		```
    * Provide authentication information user `dave` and password `secret`.
    * Click on the "Approve" button to provide permission for the OAuth server to provide token to the client.
    * If you have the [web-portal](../web-portal/README.md) project running, then you should land on the index page 
      with the OAuth code in the URL; something like `http://localhost:8090/index.html?code=RQrQTU#/`
    * Once you have the access code, you can get the actual OAuth access token by making the following POST request using curl.
    ```
    $ curl acme:acmesecret@localhost:8899/userauth/oauth/token -d grant_type=authorization_code -d client_id=acme -d redirect_uri=http://localhost:8090/index.html -d code=5YBSBs

    ```
    * Response received would be something like
    ```
    {"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDAzMzMyNTYsInVzZXJfbmFtZSI6ImFuaWwiLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImp0aSI6ImFhNzIwYzQ5LTNhNGUtNDJiNC1hYTAyLTQ1MWNkNDFjZDY1OCIsImNsaWVudF9pZCI6ImFjbWUiLCJzY29wZSI6WyJvcGVuaWQiXX0.PQgZZ_BlNG9OOLYTaWVXbcieImKy6v0BFfS6s0UQV4cNkdb_2Psqm6ZiU_feqPVn0wVq5R9aLl8ZtTliqWk_LIgwyp9vCcd6bj3upov-ga2XF7zvL_0PcVmxGS9ROYkzolbC6Kq94Xc5mCDyt298CyWtCHVtyZE0ZYF-AqNNC1lfTihNew8FYLALS84-kH4tx-3FJzujFPPw5xOAEWys-xLb13SnWiW30fcfyd44AfCbOeGXUIRX20sL2dtYXMjkeoKo5ZKO1MGlnP3Z7RGtxFwwefNRTbKz7CMyWaxa_Mk1i50Hg6z99HLBNubKZqnLKiFFFLOR2uFVMA0Wmto9Zg","token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhbmlsIiwic2NvcGUiOlsib3BlbmlkIl0sImF0aSI6ImFhNzIwYzQ5LTNhNGUtNDJiNC1hYTAyLTQ1MWNkNDFjZDY1OCIsImV4cCI6MTUwMjg4MjA1NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJmMmM3ZTU2YS0zNDVlLTRiOWItYWVkZi04YTM4............
    
	```
	
#### Implicit grants
  * Implicit grants are used in browser based application when we can't show the client secret on the browser side.
  * After opening an incognito window, paste the following URL(**Note: response_type=token**) in the browser bar. 
  	```
  	http://localhost:8899/userauth/oauth/authorize?response_type=token&client_id=acme&redirect_uri=http://localhost:8090/index.html
  	```
   * Provide authentication information user `dave` and password `secret`.
   * Click on the "Approve" button to provide permission for the OAuth server to provide token to the client.
   * The response redirect us to the redirect URL.
   	 
#### Password grant
  * The password grant is used to provide the username and password to the authorization server and get the access token directly. 
  * It typically would be used by mobile/desktop application that use a service to get the access token and have implicit access to the user's credentials.
  * Use a client like postman chrome extension to make the POST request for password grant (**Note: grant_type=password**).
  		```
 http://localhost:8899/userauth/oauth/token?grant_type=password&username=dave&password=secret&redirect_uri=http://localhost:8090/index.html
     	```
     	```
     	Use basic authentication in postman and provide the client and client_secret for the basic authentication
     	Username - acme
     	Password - acmesecret
     	``` 
  * The following curl command can be used to verify password grant
  	```
  	$ curl --request POST -u acme:acmesecret "http://localhost:8899/userauth/oauth/token?grant_type=password&username=dave&password=secret"
  	```
  * The response received is
  	```	
	{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDAzMzQ1NzksInVzZXJfbmFtZSI6ImRhdmUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZjg4MTgxN2QtODVkOS00ZGY2LWFmYzctNWY3NWIzZjk5MzAwIiwiY2xpZW50X2lkIjoiYWNtZSIsInNjb3BlIjpbIm9wZW5pZCJdfQ.ay-nV8fYnqyhmljh-w7NNs_wZG01FlKB3ZeicqKGaaZDm8f5IE4aTfqRWKvpOBiMlNe-z9ds9Fa-zIbhZiEuI00CTcUNQo-xyt-ynLmLXzs18ZeUj48kQ4z-q3Q0i3WEL3IOu3HcrOvHrDGoS5s71C1zIWnkCtlWC0WlmmxzyLLyBsNOOIeTPOccc1zdia9tflwxH1ovwmzi5pqptPPyC6jzkGlnWHVlA3_FzJFqI49WLuL8RD-x56_FCZYu0fDZ_IvQEnYOVa-sgJ3JeuwghbiNSHbHiPl808Oz87aQtsl-69Dvs8AcMNUyoGYwMmFBy7Bt3fHlhnENTWeDTuptxw","token_type":"bearer","refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJkYXZlIiwic2NvcGUiOlsib3BlbmlkIl0sImF0aSI6ImY4ODE4MTdkLTg1ZDktNGRmNi1hZmM3LTVmNzViM2Y5OTMwMCIsImV4cCI6MTUwMjg4MzM3OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImVlZGVjZmNjLTNiZWUtNGYyNC04NjhkLTE0OTUyNWIzY2ViOSIsImNsaWVudF9pZCI6ImFjbWUifQ.a8aUfkjNl5F_JaUbHZdvQ5mPs1DRVsRSAd88F-pFevQK0ECew4DrW15R8vRx4frcidqr56c1vqUiBsVRSoS4DaOkdiplN6guNLM-5-fpx-0NV1xhV9lm8YERmpsMHSXLVH-bwGMibt9fj9u_LgAWFZNOt3GwJEo4bY0ug7674dy1NtGEhQmwCXRXD2KUR9x3RN6ylyB1McXdzEA_cZS_7rxWyq5v3F3Tm-4OjRHYh9OrLhl9mk0A5EwGGW3s8oUeJDa75KjckrsypWt0jd9c9vEF3pwmQaLSzZDo58rvvFYnIyxe0E8Q3VAvOSd0hw5umW43w2VwEOf2L5EdYFi8VA","expires_in":43199,"scope":"openid","jti":"f881817d-85d9-4df6-afc7-5f75b3f99300"}
	```
	
#### Client credential grant
  * The client credential grant is used by the client themselves to get an access token without the context of the user involved.
  * This might be required if the application wants to do some book keeping activities (like changing the registered url) or gather statistics.
  * Use a client like postman chrome extension to make the POST request (**Note: grant_type=client_credentials**) for client_credentials grant. Note that **No Auth** should be selected for the authentication scheme since we are bypassing the user here.
  ```
  http://localhost:8899/userauth/oauth/token?grant_type=client_credentials&client_id=acme&client_secret=acmesecret
  ```
	
#### refresh token
  * By default, the access token provided by the authorization server is short lived and will expire based on the "expires_in" value provided.
  * If you access a protected resource with an expired token, it will respond back by saying that the token has expired.
  * In this scenario, the application can request another access token from the authorization server by using the refresh token.
  * Assume that we already received the access token for password grant, then we can use the following POST request from chrome postman extension. Note that **grant_type=refresh_token** and you need to provide the refresh_token value that was received in the response for the password grant.
  	```
  	http://localhost:8899/userauth/oauth/token?grant_type=refresh_token&client_id=client&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJkYXZlIiwic2NvcGUiOlsib3BlbmlkIl0sImF0aSI6ImY4ODE4MTdkLTg1ZDktNGRmNi1hZmM3LTVmNzViM2Y5OTMwMCIsImV4cCI6MTUwMjg4MzM3OSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImVlZGVjZmNjLTNiZWUtNGYyNC04NjhkLTE0OTUyNWIzY2ViOSIsImNsaWVudF9pZCI6ImFjbWUifQ.a8aUfkjNl5F_JaUbHZdvQ5mPs1DRVsRSAd88F-pFevQK0ECew4DrW15R8vRx4frcidqr56c1vqUiBsVRSoS4DaOkdiplN6guNLM-5-fpx-0NV1xhV9lm8YERmpsMHSXLVH-bwGMibt9fj9u_LgAWFZNOt3GwJEo4bY0ug7674dy1NtGEhQmwCXRXD2KUR9x3RN6ylyB1McXdzEA_cZS_7rxWyq5v3F3Tm-4OjRHYh9OrLhl9mk0A5EwGGW3s8oUeJDa75KjckrsypWt0jd9c9vEF3pwmQaLSzZDo58rvvFYnIyxe0E8Q3VAvOSd0hw5umW43w2VwEOf2L5EdYFi8VA
  	``` 
  	```
     	Use basic authentication in postman and provide the client and client_secret for the basic authentication
     	Username - acme
     	Password - acmesecret
    ``` 
    
### Getting protected resources
* Once you have the access token, put the value in a header called "Authorization" and value as "Bearer &lt;access_token_value&gt; and make the request.
	```     	
	curl -H "Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDAzMzQ1NzksInVzZXJfbmFtZSI6ImRhdmUiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZjg4MTgxN2QtODVkOS00ZGY2LWFmYzctNWY3NWIzZjk5MzAwIiwiY2xpZW50X2lkIjoiYWNtZSIsInNjb3BlIjpbIm9wZW5pZCJdfQ.ay-nV8fYnqyhmljh-w7NNs_wZG01FlKB3ZeicqKGaaZDm8f5IE4aTfqRWKvpOBiMlNe-z9ds9Fa-zIbhZiEuI00CTcUNQo-xyt-ynLmLXzs18ZeUj48kQ4z-q3Q0i3WEL3IOu3HcrOvHrDGoS5s71C1zIWnkCtlWC0WlmmxzyLLyBsNOOIeTPOccc1zdia9tflwxH1ovwmzi5pqptPPyC6jzkGlnWHVlA3_FzJFqI49WLuL8RD-x56_FCZYu0fDZ_IvQEnYOVa-sgJ3JeuwghbiNSHbHiPl808Oz87aQtsl-69Dvs8AcMNUyoGYwMmFBy7Bt3fHlhnENTWeDTuptxw" http://localhost:8081/user	
	```
