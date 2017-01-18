/**
 * Created by Administrator on 2017-01-17.
 */

(function(){
    'use strict';

    angular.module('chat.module', []).directive('chatDirective', ['$timeout', chat]);

    function chat($timeout) {

        function chatCtrl($scope, $timeout) {
            // if (!$scope.inputPlaceholderText) { $scope.inputPlaceholderText = 'Write your message here...'; }
            // if (!$scope.submitButtonText || $scope.submitButtonText === '') { $scope.submitButtonText = 'Send'; }
            // if (!$scope.title) { $scope.title = 'Chat'; }

            var vm = this;
            vm.isHidden = false;
            vm.theme = $scope.theme;
            vm.title = $scope.title;
            vm.panelStyle = {'display':'block'};
            vm.submitButtonText = $scope.submitButtonText;
            vm.inputPlaceholderText = $scope.inputPlaceholderText;
            vm.chatButtonClass= 'fa-angle-double-down icon_minim';
            vm.messages = $scope.messages;
            vm.userName = $scope.userName;
            vm.sendMessageFunction = function () {
                $scope.sendMessage()(vm.messageTx, vm.userName);
                vm.messageTx = '';
                scrollToBottom();
            };

            $scope.$watch('userName', function () {
                vm.userName = $scope.userName;
            });

            $scope.$watch('visible', function() { // make sure scroll to bottom on visibility change w/ history items
                scrollToBottom();
                $timeout(function() {$scope.$chatInput.focus();}, 250);
            });

            $scope.$watch('messages.length', function() {
                if (!$scope.historyLoading) {
                    scrollToBottom(); // don't scrollToBottom if just loading history
                }
                // if ($scope.expandOnNew && vm.isHidden) {
                //     toggle();
                // }
            });

            function scrollToBottom() {
                $timeout(function() { // use $timeout so it runs after digest so new height will be included
                    $scope.$msgContainer.scrollTop($scope.$msgContainer[0].scrollHeight);
                    console.log($scope.$msgContainer[0].scrollHeight);
                }, 200, false);
            }
        }

        function chatLink(scope, element) {

            scope.$msgContainer = $(element).find('.msg-container-base'); // BS angular $el jQuery lite won't work for scrolling
            scope.$chatInput = $(element).find('.chat-input');

            var elWindow = scope.$msgContainer[0];
            scope.$msgContainer.bind('scroll', _.throttle(function() {
                var scrollHeight = elWindow.scrollHeight;
                if (elWindow.scrollTop <= 10) {
                    scope.historyLoading = true; // disable jump to bottom
                    scope.$apply(scope.infiniteScroll);
                    $timeout(function() {
                        scope.historyLoading = false;
                        if (scrollHeight !== elWindow.scrollHeight) // don't scroll down if nothing new added
                            scope.$msgContainer.scrollTop(360); // scroll down for loading 4 messages
                    }, 150);
                }
            }, 300));
        }

        return {
            restrict: 'E',
            templateUrl:'/res/src/templates/chatt.html',
            replace:'true',
            scope:{
                theme:'@',
                submitButtonText:'@',
                inputPlaceholderText:'@',
                title:'=',
                messages:'=',
                userName:'=',
                visible:'=',
                sendMessage:'&'
            },
            link:chatLink,
            controller:chatCtrl,
            controllerAs: 'vm'
        };
    }

    angular.module('chat.services', []).factory('chatService', ['$rootScope', chatService]);

    function chatService($rootScope) {
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
    }
})();
