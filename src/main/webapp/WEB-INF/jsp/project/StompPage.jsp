<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>
            com.munseop STOMP 테스트 페이지! !!!!
        </title>
        <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
        <script src="http://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
        <jsp:include page="Common/Script.jsp" />
        <script type="application/javascript">
            var ws;
            var stomClient;
            var username;

            $(document).ready(function () {
                
            });

            function connect() {
                ws = new WebSocket('ws://localhost:8080/chat.ws');
                //ws = new SockJS('http://localhost:8080/questStomp');
                stomClient = Stomp.over(ws);

                var connectHeader = {
                    login:'shoong92',
                    password:'s7856',
                    'groupNo':'g#001',
                    'clientId':'0001'
                };

                stomClient.connect(connectHeader, function (frame) {
                    username = frame.headers["user-name"];
                    writeToScreen(username);

                    stomClient.subscribe("/topic/chat.ws.message.ex", function (message) {
                        console.log("rev(topic) : " + message.body);
                        writeToScreen("rev(topic) : " + message.body);
                    });

//                    stomClient.subscribe("/user/queue/private", function (message) {
//                        console.log("rev(user) : " + message.body);
//                        writeToScreen("rev(user) : " + message.body);
//                    });

                    stomClient.subscribe("/user/exchange/amq.direct/chat.ws.message.ex.afc469a7-aa12-ad24-dea2-7b1c939f3afe", function (message) {
                        console.log("rev(exchange) : " + message.body);
                        writeToScreen("rev(exchange) : " + message.body);

                        stomClient.subscribe("/app/chat.ws.ex/1");
                    });

                    stomClient.subscribe("/app/chat.ws.ex/1", function(message) {
                        console.log("app : " + message);
                        writeToScreen("app : " + message);
                    });
                }, function (error) {
                    console.log("error : " + error);
                    writeToScreen("error : " + error);
                });
            }
            
            function disconnect() {
                stomClient.disconnect(function () {
                    writeToScreen("Disconnected!!!")
                });
            };

            function  sendMessage() {
                stomClient.send("/app/chat.ws.message.ex", {}, "stomp send ok");
            }

            function sendMessageTo() {
                stomClient.send("/app/chat.ws.private.ex." + username, {}, "stomp send to other ok!!!");
            }

            function writeToScreen(message) {
                var pre = document.createElement("p");
                pre.style.wordWrap = "break-word";
                pre.innerHTML = message;

                output.appendChild(pre);
            }
        </script>
    </head>
    <body>
    <jsp:include page="Common/Top.jsp" />

    <h1 style="text-align: center">Contents</h1>

    <div style="text-align: center;">
        <form action="">
            <input onclick="connect()" value="connect" type="button">
            <input onclick="sendMessage()" value="Send" type="button">
            <input onclick="sendMessageTo()" value="SendTo" type="button">
            <input onclick="disconnect()" value="disconnect" type="button">
            <input id="textID" name="message" value="Hello WebSocket!" type="text"><br>
        </form>
    </div>
    <div id="output"></div>

    <jsp:include page="Common/Bottom.jsp" />
    </body>
</html>
