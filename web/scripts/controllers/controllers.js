/**
 * Created by kuerndan on 02/09/14.
 */
angular.module('messageBoard.controllers',[])
        
        .controller('MessageListController',function($scope,$state,popupService,$window,Message){
            $scope.msgOrder = "uniqueId";
            $scope.messages = Message.query();

            $scope.newMessage = new Message();

            $scope.addMessage=function(){
                $scope.newMessage.$save(function(){
                    $scope.messages = Message.query();
                });
            }

            $scope.deleteMessage=function(message){
                var id = message.uniqueId;
                message.$delete({id: id},function(){
                    $scope.messages = Message.query();
                });
            }

        })
        
        .controller('MessageViewController',function($scope,$state,$stateParams,Message){

            $scope.message=Message.get({id:$stateParams.id});

});