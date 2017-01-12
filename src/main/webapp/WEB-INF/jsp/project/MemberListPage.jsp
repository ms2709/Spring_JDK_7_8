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

    <div ng-app="chatModule" ng-controller="ctrlMemberList">
        <table class="table-bordered">
            <tr>
                <th>Select</th>
                <th>Login Id</th>
                <th>User Name</th>
            </tr>
            <tr ng-repeat="member in members">
                <td>
                    <input type="checkbox" ng-model="member.isSelected" />
                </td>
                <td>{{member.loginId}}</td>
                <td>{{member.userName}}</td>
            </tr>
        </table>

        <div>
            <form id="formCreateRoom" method="post" action="" enctype="multipart/form-data">
                <p>group name:</p>
                <input type="text" ng-model="group.name" />
                <input type="hidden" id="hroom" name="room" />
                <button ng-click="createRoom(group.name)">Create Room</button>
            </form>
        </div>
    </div>
    <jsp:include page="Common/Bottom.jsp" />
    </body>

</html>

<script type="text/javascript">
    angular.module('chatModule', ['angularUUID2'])
        .filter('selectedMembers', ['members', function (members) {

        }])
        .controller('ctrlMemberList', function ($rootScope, $scope, $http, uuid2) {
        $scope.members = [];

        $scope.getMembers = function () {
            $http.post('/member/memberList.do', {}).then(function(data){
                data.data.forEach(function (item) {
                    item.isSelected = false;
                });
                $scope.members = data.data;

            }, function (error) {

            }, function (notify) {

            });
        }

        $scope.createRoom = function (groupName) {
            $scope.room = {
                roomName : groupName,
                roomId : uuid2.newguid(),
                createDate : new Date(),
                status : 'create',
                roomDetails :[]
            }

            $scope.members.forEach(function (item) {
                if(item.isSelected == true) {
                    $scope.room.roomDetails.push({
                        roomId : $scope.room.roomId,
                        loginId : item.loginId
                    });
                }
            });

            console.log($scope.group);

            var form = $('#formCreateRoom');
//            var formData = new FormData();
//            formData.append("room", $scope.room);
            form.attr("action", "/room/createRoom.do");
            $('#hroom').val(angular.toJson($scope.room));
            form.submit();

//            $http.post("/room/createRoom.do", $scope.room).then(function (res) {
//
//            }, function (error) {
//
//            }, function (notify) {
//
//            });
        }
        $scope.getMembers();
    });
</script>
