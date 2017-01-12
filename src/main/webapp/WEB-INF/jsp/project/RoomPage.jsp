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
        <jsp:include page="Common/Script.jsp" />

    </head>
    <body>
    <jsp:include page="Common/Top.jsp" />

    <div ng-app="chatModule" ng-controller="ctrlChat">
        <div>
            <button ng-click="initSock()">connect</button>
            <button ng-click="sendMessageToRoom()">sendToRoom</button>
            <button ng-click="sendMessageToUser()">sendToRoom</button>
            <button ng-click="disconnect()">disconnect</button>
        </div>
    </div>
    <jsp:include page="Common/Bottom.jsp" />
    </body>

</html>

<script type="text/javascript">
    angular.module('chatModule', ['angularUUID2', 'chatModule.services']).controller('ctrlChat', function ($rootScope, $scope, $http, uuid2, chat) {
        var room = decodeURIComponent(document.cookie);
        $scope.room = angular.fromJson(room.replace('room=', ''));

        $scope.initSock = function() {
            chat.init('ws://localhost:8080/chat.ws');
            chat.connect({}, function (frame) {
                $scope.username = frame.headers['user-name'];

                chat.subscribe("/topic/chat.ws.message." + $scope.room.roomId.toUpperCase(), function (message) {
                   console.log(message);
                });

                chat.subscribe("/user/exchange/amq.direct/chat.ws.message." + $scope.room.roomId.toUpperCase(), function (message) {
                    console.log(message);
                });
            }, function (error) {
                console.log(error);
            });
        }

        $scope.disconnect = function () {
            chat.disconnect(function () { console.log("disconnected!!");});
        }

        $scope.sendMessageToRoom = function () {
            $scope.msg = {
                roomId:$scope.room.roomId.toUpperCase(),
                message:'TEST message 입니다!',
                sendUserId:$scope.username,
                recvuserId:'',
                room:$scope.room,
                status:'send'
            };
            console.log($scope.msg);

            chat.send("/app/chat.ws.message." + $scope.room.roomId.toUpperCase(), {}, angular.toJson($scope.msg));
        }

        $scope.sendMessageToUser = function () {
            $scope.msg = {
                roomId:$scope.room.roomId.toUpperCase(),
                message:'TEST message 입니다!',
                sendUserId:$scope.username,
                recvuserId:'guest1',
                room:$scope.room,
                status:'send'
            };
            console.log($scope.msg);

            chat.send("/app/chat.ws.private." + $scope.msg.recvuserId, {}, angular.toJson($scope.msg));
        }
    });

    angular.module('chatModule.services', [])
        .factory('chat', ['$rootScope', function($rootScope) {
            var stompClient;

            var socket = {

                init: function(url) {
                    stompClient = Stomp.over(new WebSocket(url));
                },
                connect: function(header, successCallback, errorCallback) {

                    stompClient.connect(header, function(frame) {
                        $rootScope.$apply(function() {
                            successCallback(frame);
                        });
                    }, function(error) {
                        $rootScope.$apply(function(){
                            errorCallback(error);
                        });
                    });
                },
                disconnect: function (disconnected) {
                    stompClient.disconnect(function () {
                        disconnected();
                    });
                },
                subscribe : function(destination, callback) {
                    stompClient.subscribe(destination, function(message) {
                        $rootScope.$apply(function(){
                            callback(message);
                        });
                    });
                },
                send: function(destination, headers, object) {
                    stompClient.send(destination, headers, object);
                }
            }

            return socket;

        }]);
</script>
