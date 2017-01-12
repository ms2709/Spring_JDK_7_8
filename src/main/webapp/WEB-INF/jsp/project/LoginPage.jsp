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

    <div ng-app="chatModule" ng-controller="ctrlLogin">
        <form:form id="formLogin" modelAttribute="loginUser" action="">
            <div class="LoginInputLeft">
                <label>User ID</label>
                <label>Password</label>
            </div>
            <div class="LoginInputRight">
                <form:input path="loginId" cssClass="LoginInput" id="txtID" placeholder="로그인아이디" tabindex="1"  />
                <form:password path="password" cssClass="LoginInput" id="txtPass" placeholder="비밀번호" style="margin-top:3px;" tabindex="2" enter-key-event="login()" />
                <input type="button" ng-click="login()" tabindex="3" value="로그인"/></div>
                <span class="form-inline"><input type="checkbox" class="form-control" /><label>사용자 ID저장</label></span>
            </div>
        </form:form>
    </div>
    사용자로그인
    <jsp:include page="Common/Bottom.jsp" />
    </body>

</html>

<script type="text/javascript">
    angular.module('chatModule', []).controller('ctrlLogin', function ($rootScope, $scope) {
        $scope.login = function(){
            var form = $("#formLogin");

            form.attr("action", "/loginMember.do");
            form.submit();
        }
    });
</script>
