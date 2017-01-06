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
            com.munseop WEB SOCKET 테스트 페이지! !!!!
        </title>
        <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
        <script src="http://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
        <jsp:include page="Common/Script.jsp" />
        <script type="application/javascript">
            var ws;

            $(document).ready(function() {
                //ws = new WebSocket('ws://localhost:8080/quest.ws');
                ws = new SockJS('http://localhost:8080/quest');

                ws.onopen    = function (data) { writeToScreen(data.data);}
                ws.onmessage = function (data) { writeToScreen(data.data); }
                ws.onclose   = function (data) { writeToScreen(data.data); }
            });

            window.onbeforeunload = function() {
                websocket.close()
            };

            function sendMessage(){
                ws.send('성공');
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
            <input onclick="sendMessage()" value="Send" type="button">
            <input id="textID" name="message" value="Hello WebSocket!" type="text"><br>
        </form>
    </div>
    <div id="output"></div>

    <jsp:include page="Common/Bottom.jsp" />
    </body>
</html>
