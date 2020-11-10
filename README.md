# Overview
This repository implements a simple online community website in Java and Spring Boot
with Model-view-controller design pattern. Currently, this application can show a list
of all the posted questions in several pages. Users can post their own questions, reply
questions, reply to comments, and up vote or down vote to questions and comments. Users
can log in and create an account using their github accounts. 

# Structure of the Project
### src/main/java/com/jw22/community/
* advice: this directory contains the exception handlers for server requests and responses
* cache: this directory stores some information that should be displayed on the pages
* controller: this directory contains the controllers that handle the request from user and
display the correct web page.
* dto: this directory contains classes that facilitate the data transfer between the database and the web pages
* enums: this directory stores some predefined data types
* exception: this directory handles customized exceptions
* interceptor: this directory checks if the user has an unterminated when first visiting the website
* mapper: this directory has the functions that interact with the MySQL database
* 