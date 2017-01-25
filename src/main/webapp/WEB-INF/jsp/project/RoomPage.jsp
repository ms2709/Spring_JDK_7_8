<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <title>
            com.munseop WEB SOCKET 테스트 페이지! !!!!
        </title>
        <jsp:include page="Common/Script.jsp" />

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="/res/src/css/style.css">
        <link rel="stylesheet" href="/res/src/css/themes.css">

        <script src="/res/src/scripts/chatd.js"></script>
    </head>
    <body>
    <%--<jsp:include page="Common/Top.jsp" />--%>
    <div class="chat-container" ng-app="app"  ng-controller="ctrl as vm" class="ng-scope">
        <chat-directive theme="chat-th-material"
                        submit-button-text="Send Message"
                        input-placeholder-text="You can write here ..."
                        send-message="vm.sendMessage"
                        messages="vm.messages"
                        user-name="vm.userName"
                        visible="true"
                        title="vm.title"></chat-directive>
    </div>
    <%--<jsp:include page="Common/Bottom.jsp" />--%>
    </body>

</html>

<script type="text/javascript">
    angular.module('app', ['chat.module', 'chat.services']).controller('ctrl', ctrl);

    function ctrl($http, chatService){
        var vm = this;
        vm.title = "화재발생";
        vm.messages = [];

        var room = decodeURIComponent(document.cookie);
        vm.room = angular.fromJson(room.replace('room=', ''));

        vm.initSock = function () {
            chatService.init('ws://192.168.0.6:8080/chat.ws');
            chatService.connect({}, function (frame) {
                vm.userName = frame.headers['user-name'];

                chatService.subscriber("/topic/chat.ws.message." + vm.room.roomId.toUpperCase(), function (message) {
                    message.ack();
                    var msg = angular.fromJson(message.body);
                    if(vm.userName !== msg.sendUserId) {
                        vm.messages.push(msg);
                    }
                }, {ack:"client"});
//
//                chatService.subscribe("/user/exchange/amq.direct/chat.ws.message." + vm.room.roomId.toUpperCase(), function (message) {
//                    vm.messages.push(message);
//                });

            }, function (error) {
                console.log(error);
            });
        };

        vm.initSock();

        this.sendMessage = function (message, userName) {
            vm.msg = {
                roomId:vm.room.roomId.toUpperCase(),
                message: message,
                sendUserId:userName,
                recvuserId:'',
                room:vm.room,
                status:'send'
            };

            chatService.sender("/app/chat.ws.message." + vm.room.roomId.toUpperCase(), {}, angular.toJson(vm.msg));

            if(message && message.message !== '' && userName){
                vm.messages.push(vm.msg);
            }
        }
    }
</script>
