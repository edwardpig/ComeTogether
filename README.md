# Welcome to My App!

This is a Spring Boot app, developed using:

	* STS Eclipse version 4.11.1.RELEASE
	* Maven version 3.8.2
	* Oracle Java version 1.8.0_241

There are at least two different approaches to running the app locally.

## Approach 1

	1. Clone the repository (but if hadn't already done that, you wouldn't be reading this!)
	2. Make sure you have Java version 1.8.0_241 or above installed and $JAVA_HOME set appropriately
	3. Make sure you have Maven version 3.8.2 or above installed and $M2_HOME set appropriately, and $M2_HOME/bin in your $PATH
	4. Build the UserInfo-0.0.1-SNAPSHOT.war by opening a command window/bash shell and running
	
		mvn clean install
		
		In the directory containing this README file
	5. If the build is successful, the resulting UserInfo-0.0.1-SNAPSHOT.war should be built to the 'target' subfolder of the directory containing this README file
	6. Drop the UserInfo-0.0.1-SNAPSHOT.war file into your favorite servlet container
	7. Using a tool like Postman, or your favorite web browser, navigate to
	
		<context_root>/userInfo-service/userinfo
		
		to see the lovely JSON required for the Code Test
	8. Alternatively, navigate to the following URL in your favorite web browser
	
		<context_root>/userInfo-service/swagger-ui/
		
		to see a lovely Swagger UI, which provides a friendly user interface to call the REST endpoint which will return the lovely JSON required for the Code Test
		
## Approach 2

Since you find a Docker image to be really impressive, open a command prompt on a machine which has the Docker command line running, and enter

	docker run -dp 8700:8700 edwardpig/come-together
	
Once the image is downloaded and running, navigate to the following URL in a tool like Postman or your favorite web browser

	http://localhost:8700/userInfo-service/userinfo
	
to see the lovely JSON required for the Code Test.  Or alternatively, navigate to the following URL in your favorite web browser

	http://localhost:8700/userInfo-service/swagger-ui/
	
to see a lovely Swagger UI, which provides a friendly user interface to call the REST endpoint which will return the lovely JSON required for the Code Test