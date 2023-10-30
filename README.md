# The repository is a duplication of the original submission of https://github.com/OntarioTech-CS-program/w23-csci2020u-assignment02-chen-zheng
# Assignment 02 - Web Chat Server (Instructions)
> Course: CSCI 2020U: Software Systems Development and Integration

This is the template for your Assignment 02.

## Project Information 
Shian Li Chen (Student ID: 100813628)

Xuan (Sean) Zheng (Student ID: 100789833)

This project is a web chat webpage that allows users to create new chat rooms or enter existing chat rooms to chat privately with their friends or other users in the room. This project uses Web Sockets and Java to create and allow for users to send messages in the different chat rooms. Chat rooms are identified by unique room IDs and users are only able to read the messages from and chat with users in the same room. HTML, CSS and JavaScript were used to create a visually appealing and user-friendly UI that allows users to quickly join and create new chat rooms. Users also have the opportunity to view any new rooms that are created by clicking the refresh button. An about page was also included to give recognition to the creators of the page. The about page includes the users GitHub profiles. Below is a screenshot of 3 users who have joined and are talking in a chat room:

This image is from the perspective of Harry:

![image](https://user-images.githubusercontent.com/90335714/230750846-bc6a973e-d311-4dd8-aa3a-1b38a59c2895.png)

This image is from the perspective of Nancy:

![image](https://user-images.githubusercontent.com/90335714/230750852-b033fc52-4064-4ccb-9e67-17eb6ce418d5.png)

This image is from the perspective of Gary, who arrived later:

![image](https://user-images.githubusercontent.com/90335714/230750869-e66d0f68-d967-4ba8-80ab-d1eebcee0c53.png)

This is what the user sees when they click the ``Enter an Existing Room`` button:

![image](https://user-images.githubusercontent.com/90335714/230750773-2f396e9c-b647-4b14-898f-9293b3e4defd.png)

This is what the user sees when all the other users have left the room:

![image](https://user-images.githubusercontent.com/90335714/230750880-147fdf53-df80-414e-9fd3-9126dd6a1bd4.png)

This is before and after the ``Refresh Rooms`` button has been pressed:

![image](https://user-images.githubusercontent.com/90335714/230750731-4c75c6f4-34b6-4b1b-9033-3c998fa735f4.png)
![image](https://user-images.githubusercontent.com/90335714/230750739-f4745c2a-c607-415c-9281-430918def5f7.png)

## Improvements
**UI/Interactions:** In terms of the user interface, one of the changes that was applied was the use of icons, from font awesome, to help add more character to the page. Another change was adding a different colour scheme to help reduce the harsh contrast of orange and blue in the original page. A specific font family was used and the view is also resizable. Other more major changes to the UI include a Send button being added to the page to give the user the option of pressing either the Enter key on their keyboard or the Send button to send their message to the chat. An Enter an Existing Room button was added to help users easily switch rooms. A Refresh Rooms button was added to help the user view any new rooms that were created. A new feature that was added was that if the user typed /users in the chat it would list all the current users in the chat room on the chat log. When a user leaves the chat room and the /users message is sent again the list displayed in the chat log will show the unpdated list of current users in the room. 

**Back End:** A new RoomServlet Java class was added to help allow for quick and easy access to the list of available or created room codes that will be used by and displayed by pressing the Refresh Rooms button.

## How to Run
1. Clone the repository into a folder of your choice using git clone and the url for this GitHub page. (Make sure that the folder name has no spaces in it)
2. Navigate into your cloned com.example.webchatserver folder and select the ChatServer hava file to open. ![image](https://user-images.githubusercontent.com/90335714/230753400-263b53ac-ee64-4f04-9152-2c1fb6996227.png)

3. Prior to running you need to set up you Glassfish configurations by selecting the drop down next to the green hammer icon on IntelliJ (by default it should be on Current File). ![image](https://user-images.githubusercontent.com/90335714/225509626-02f4b242-5a27-4b23-b8e3-d7f37ea3ef41.png)

You then add a new configuration using the add symbol and selecting the glassfish local server option. The configurations should be changed to match the first image below. Also make sure to add the war exploded artifact (shown in the second image).
![image](https://user-images.githubusercontent.com/90335714/230753460-7ec16073-54fc-41c5-8555-6d25fa8f32ea.png)
![image](https://user-images.githubusercontent.com/90335714/230753471-572933d8-9278-493b-8b02-0046551f408a.png)

4. Once you have set up the configurations run the GlassFish server by pressing the green play button (you need to be on a java file for this to work). This may take a couple moments to load.
5. Once loaded you will be on the `http://localhost:8080/WSChatServer-1.0-SNAPSHOT/` endpoint which should look similar to what is shown below:
![image](https://user-images.githubusercontent.com/90335714/230753554-8ba6cab0-8242-4903-a5f1-61810f5a59cf.png)

6. Now you can start chatting! Press the Create and Join New Room button to create a new room and join it. Press the Refresh Rooms button to see if there are any rooms that have already been created. Enter an existing room by pressing the Enter an Existing Room button and input the desired room code you would like to join.
7. Once in a room you can type in the input area, which is the white box that has the send button at the end of it:
![image](https://user-images.githubusercontent.com/90335714/230753660-93a6a155-d7ab-4f59-9b35-94ad69c91dda.png)

8. You can send messages to the chat room by pressing the Enter key on your key board or pressing the Send button.
9. If you want to view the users currently in the room, after entering your own username you can type /users in the input box and the log will show all the users currently in the room. 

## Other Resources 

https://fontawesome.com/icons

This font awesome library was used to add icons that added to the aesthetics of the UI.

https://github.com/OntarioTech-CS-program/W23-LectureExamples

Inspiration for some of the code was drawn from the lecture examples shown above.

## Overview
You want to create a web chat server with multiple rooms. This will allow you and your friends to chat privately.

- Check the `Canvas/Assingments/Assignment 02` for more the detailed instructions.

### WebChatServer - Endpoints

**Connect to the websocket**

From the `ChatServer` class. This will create a new client connect to the web server. The server and client communicate using `json` messages.
- `ws://localhost:8080/WSChatServer-1.0-SNAPSHOT/ws/{roomID}`


**GET a new (unique) room code**

From the `ChatServlet` class. This will return a `text/plain` content type.
- `http://localhost:8080/WSChatServer-1.0-SNAPSHOT/chat-servlet`
See a sample of the response data:
```
1B9FN
```

### WebChatServer - client

Your client is in the `webapp` folder, when started the application will run at `http://localhost:8080/WSChatServer-1.0-SNAPSHOT/`; which will load the `index.html` file.

Your client-side code will be in the `js/main.js` javascript file.

> Obs. Feel free to create other helper classes as you see fit.
> 



