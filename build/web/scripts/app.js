/**
 * Created by kuerndan on 02/09/14.
 */

angular.module('messageBoard',['ui.router','ngResource','messageBoard.controllers','messageBoard.services']);

angular.module('messageBoard').config(function($stateProvider,$httpProvider){
    $stateProvider.state('MessageList',{
        url:'/messages',
        templateUrl:'views/MessageList.html',
        controller:'MessageListController'
    }).state('MessageDetail',{
       url:'/messages/:id',
       templateUrl:'views/MessageDetail.html',
       controller:'MessageDetailController'
    });
}).run(function($state){
   $state.go('MessageList');
});