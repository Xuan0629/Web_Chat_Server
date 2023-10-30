let ws;

function newRoom(){
    // calling the ChatServlet to retrieve a new room ID
    let callURL= "http://localhost:8080/WSChatServer-1.0-SNAPSHOT/chat-servlet";
    fetch(callURL, {
        method: 'GET',
        headers: {
            'Accept': 'text/plain',
        },
    })
        .then(response => response.text())
        .then(response => enterRoom(response)); // enter the room with the code
}
function enterRoom(code){
    // create the web socket using the unique room code
    ws = new WebSocket("ws://localhost:8080/WSChatServer-1.0-SNAPSHOT/ws/"+code);

    // function to be implemented when a room is entered
    ws.onopen = function (event){
        // prints the list of room codes every time a new room is made
        let list = document.getElementById("room-codes");
        if(document.getElementById(code) === null){
            list.innerHTML += "<ul>" +
                "<li id=\""+code.trim()+"\">"+code+"</a></li>" +
                "</ul>";
        }
        // changes the title of the room based on the room code
        let page_title = document.getElementById("entered_page");
        page_title.innerHTML = "Welcome to Chat Room: " + code + "!";
        // makes sure that the log area is cleared so that entering the new room starts with a fresh log
        document.getElementById("log").value = "";
    }

    // parse messages received from the server and update the UI accordingly
    ws.onmessage = function (event) {
        console.log(event.data);
        // parsing the server's message as json
        let message = JSON.parse(event.data);

        // handles message and adds the message to the log area using the format shown
        document.getElementById("log").value += "[" + timestamp() + "] " + message.message + "\n";
    }
}
document.getElementById("input").addEventListener("keyup", function (event) {
    // handles the situation where the enter key is pressed
    if (event.keyCode === 13) {
        // creates a JSON object using the text in the input area as the message
        let request = {"type":"chat", "msg":event.target.value};
        // sends the message to the web socket to be processed
        ws.send(JSON.stringify(request));
        // clears the input area to allow for the creation of a new message
        event.target.value = "";
    }
});
function sendMessage() {
    // handles the situation where the send button is pushed
    // similar to functionality for the enter key press but is for a send button
    // gets the message from the input element
    let inputElement = document.getElementById("input");
    // removes any whitespace
    let message = inputElement.value.trim();
    // passes the message to the web socket to be processed
    if (message !== "") {
        let request = {"type": "chat", "msg": message};
        ws.send(JSON.stringify(request));
        // clears the input area to allow for the creation of a new message
        inputElement.value = "";
    }
}

function timestamp() {
    // creation of a time stamp to be added to the message when printed on the log
    var d = new Date(), minutes = d.getMinutes();
    if (minutes < 10) minutes = '0' + minutes;
    return d.getHours() + ':' + minutes;
}

function joinRoom() {
    // handles the situation when the join room button is pressed
    // creates a prompt that encourages the user to enter the room code that they want to enter
    let code = prompt("Enter the room code:");
    if (code !== null && code.trim() !== "") {
        // enters the room based on the room code that the user entered
        enterRoom(code);
    }
}
function refreshRooms(){
    // calling the RoomServlet to retrieve the list of roomIDs
    let callURL= "http://localhost:8080/WSChatServer-1.0-SNAPSHOT/room-servlet";
    fetch(callURL, {
        method: 'GET',
        headers: {
            'Accept': 'text/plain',
        },
    })
        .then(response => response.text())
        .then(response => {
            // handles the list of roomIDs and converts it to an array to be looped through
            response = response.replace(/[\[\]']+/g,'');
            response = response.split(",");
            console.log(response);
            let word;
            // loops through list of roomIds
            for(var j = 0; j<response.length; j++){
                word = response[j];
                word = word.replace(/[\[\]']+/g,'');
                word = word.trim();
                console.log(word);
                // adds roomIds in the list to the list of room codes shown to the user (UI list)
                let list = document.getElementById("room-codes");
                // only adds roomIds to the list in the UI if the list of room codes doesn't already have the room code
                if(document.getElementById(word) === null){
                    list.innerHTML += "<ul>" +
                        "<li id=\""+word+"\">"+word+"</a></li>" +
                        "</ul>";
                }
            }
        });
}
