/**
 * Created by kuerndan on 02/09/14.
 */

angular.module('messageBoard',['ui.router','ngResource','messageBoard.controllers','messageBoard.services']);

angular.module('messageBoard').config(function($stateProvider,$httpProvider){
    $stateProvider.state('messages',{
        url:'/messages',
        templateUrl:'views/messages.html',
        controller:'MessageListController'
    }).state('viewMessage',{
       url:'/messages/:id',
       templateUrl:'views/message-view.html',
       controller:'MessageViewController'
    });
}).run(function($state){
   $state.go('messages');
});