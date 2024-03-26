STEPS ON HOW TO RUN A SPRING BOOT CODE IN DOCKER

There are several steps on how to dockerize a spring boot application, below are some of the steps to follow;

1.   Docker installation
   The first step  is to install a desktop docker inside your computer from their official website.

2.   CREATE A DOCKER FILE
   Create a dockerfile in the root directory of your spring boot application. This docker file specifies how you want to build the docker image for your application. Inside your docker file, below are some steps to follow to build your docker file;

   You need to use an open jdk based image, this is where you will input the jdk version you are using in building your application.

     a. Secondly, you have to set the working directory in your container.

     b. Thirdly, you need to copy the packaged spring boot application JAR file into the container.

     c.  Then, you need to expose the port that the application is running on. For example, if the application is running on port 8089, it has to be exposed using the keyword EXPOSE 8089.

     d.  In conclusion, you need to specify the command to run the springBoot application. Commands like [java”, “-jar”, “app.jar].

 3.    BUILD THE DOCKER IMAGE
        After creating the docker file, open your terminal or command prompt, then navigate to      the directory containing the spring boot project and the docker file, then run the below command  inside your terminal or your command prompt:

		    “docker build -t your-image-name”.

        You will represent “your-image-name” with the actual name you are giving to the docker image you wanted to built.

 4.	  RUN YOUR DOCKER CONTAINER
        After the docker image has been built successfully, you can now run the docker container from the image built earlier in your terminal or the command prompt using the following command;

	      “docker run -p 8080:8080 your-image-name”.

          Replace “your-image-name” with your actual image name.

This is a brief summary on how to run a spring boot code in docker.
