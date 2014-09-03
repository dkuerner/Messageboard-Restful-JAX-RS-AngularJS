/**
 * Created by kuerndan on 02/09/14.
 */
angular.module('messageBoard.controllers',[])
        
        .controller('MessageListController',function($scope,Message,$stateParams){
            $scope.msgOrder = "uniqueId";
            $scope.messages = Message.query(); // override of default GET to wrap returned objects to an array

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
        
        .controller('MessageDetailController',function($scope,Message,$stateParams){

            $scope.message=Message.get({id:$stateParams.id});

});