package com.example.webchatserver;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This is a class that has services
 * In our case, we are using this to generate and have access to the
 * list of the unique room IDs**/
@WebServlet(name = "roomServlet", value = "/room-servlet")
public class RoomServlet extends HttpServlet {
    // creates an instance of the ChatServlet
    private ChatServlet chatserv;
    // creates a reference to the rooms set of strings in the ChatServlet class
    public Set<String> rooms = chatserv.rooms;

    /**
     * Method prints the list of unique codes to the http://localhost:8080/WSChatServer-1.0-SNAPSHOT/room-servlet link
     * **/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // sets the content type of the servlet
        response.setContentType("text/plain");

        // sends the list of room codes as the response's content
        PrintWriter out = response.getWriter();
        // outputs the list of random codes to the http://localhost:8080/WSChatServer-1.0-SNAPSHOT/room-servlet link
        out.println(rooms.toString());
    }

    public void destroy() {
    }
}