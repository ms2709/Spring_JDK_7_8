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

    <div ng-app="chatModule" ng-controller="ctrlRoomList">
        <div>
            <button ng-click="getRoomList()">connect</button>
        </div>
        <div>
        <table class="table-bordered">
            <tr>
                <th>roomId</th>
                <th>roomName</th>
                <th>select</th>
            </tr>
            <tr ng-repeat="room in rooms">
                <td>{{room.roomId}}</td>
                <td>{{room.roomName}}</td>
                <td>
                    <form id="formEnterRoom" method="post" action="" enctype="multipart/form-data">
                        <input type="hidden" id="hroom" name="room" />
                        <button ng-click="enterRoom(room)">입장</button>
                    </form>
                </td>
            </tr>
        </table>
        </div>
    </div>
    <jsp:include page="Common/Bottom.jsp" />
    </body>

</html>

<script type="text/javascript">
    angular.module('chatModule', ['angularUUID2'])
        .controller('ctrlRoomList', function ($rootScope, $scope, $http, uuid2) {
            $scope.rooms = [];

            $scope.getRoomList = function () {
                $http.post("/room/roomList.do", {}).then(function(res) {
                    $scope.rooms = res.data;

                }, function (error) {

                }, function (notify) {

                })
            }

            $scope.enterRoom = function (room) {
                var form = $('#formEnterRoom');
                form.attr("action", "/room/selectRoom.do");
                $('#hroom').val(angular.toJson(room));
                form.submit();
            }
        });
</script>
