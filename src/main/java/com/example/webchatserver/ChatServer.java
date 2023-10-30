package com.example.webchatserver;


import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a web socket server, a new connection is created and it receives a roomID as a parameter
 * **/
@ServerEndpoint(value="/ws/{roomID}")
public class ChatServer {

    // contains a static List of ChatRoom used to control the existing rooms and their users
    private Map<String, String> usernames = new HashMap<>();
    private static Map<String, String> roomList = new HashMap<>();

    // keeps track of usernames and roomID
    public static Map<String, String> list_users = new HashMap<>();

    @OnOpen
    public void open(@PathParam("roomID") String roomID, Session session) throws IOException, EncodeException {
        // format of the first message to be logged when a new user joins a room
        // includes the roomID
        session.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server " + roomID +"): Welcome to the chat room. Please state your username to begin.\"}");
        // puts the roomID into the map of roomLists with sessionID as the key and the roomID as the value
        roomList.put(session.getId(),roomID);
    }

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
        // initializes userID to the sessionID
        String userId = session.getId();
        // initializes roomId to the value returned when passing the userID as a key to the roomList map
        String roomId = roomList.get(userId);
        // checks whether usernames map has the userId as a key
        if (usernames.containsKey(userId)) {
            String username = usernames.get(userId);
            // removes username from list_users map
            list_users.remove(username);
            // removes the userId from the usernames map
            usernames.remove(userId);
            //broadcasts that the user left the server to all other users in the room
            for (Session peer : session.getOpenSessions()){
                // checks whether peer is in the same room
                if(roomList.get(peer.getId()).equals(roomId)) {
                    peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server): " + username + " left the chat room.\"}");
                }
            }
        }
    }

    @OnMessage
    public void handleMessage(String comm, Session session) throws IOException, EncodeException {
        // initializes userId and roomID
        String userId = session.getId();
        String roomID = roomList.get(userId);
        // creates a JSON object
        JSONObject jsonmsg = new JSONObject(comm);
        // gets the value of the type key for the JSON object
        String type = (String) jsonmsg.get("type");
        // gets the value of the message key for the JSON object
        String message = (String) jsonmsg.get("msg");

        // handle the messages
        // checks whether usernames map has the userId
        // for messages that are not the user's first message
        if(usernames.containsKey(userId)){
            String username = usernames.get(userId);
            System.out.println(username);
            for(Session peer: session.getOpenSessions()){
                // only sends messages to those in the same room
                if(roomList.get(peer.getId()).equals(roomID)) {
                    // prints usernames in room if user types /users
                    if(message.contains("/users"))
                    {
                        String peer_List = "";
                        for(String key:list_users.keySet())
                        {
                            if(list_users.get(key).equals(roomID)){
                                peer_List += key + ",";
                            }
                        }
                        session.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server): " + peer_List + "\"}");
                        break;
                    }
                    // normal message
                    else{
                        peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(" + username + "): " + message + "\"}");
                    }
                }
            }
        }
        // for messages that are the user's first message
        else{
            usernames.put(userId, message);
            list_users.put(message, roomID);
            session.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server ): Welcome, " + message + "!\"}");
            for(Session peer: session.getOpenSessions()){
                // only announces to those in the same room as me that I have joined the room, excluding myself
                if((!peer.getId().equals(userId))&&(roomList.get(peer.getId()).equals(roomID))){
                    peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server): " + message + " joined the chat room.\"}");
                }
            }
        }
    }
}