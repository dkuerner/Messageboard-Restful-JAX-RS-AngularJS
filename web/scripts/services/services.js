/**
 * Created by kuerndan on 02/09/14.
 */

angular.module('messageBoard.services',[])
        .factory('Message',function($resource){
            return $resource('http://localhost:8080/message-board/app/messages/:id',{id:'@_id'},{
                update: {
                    method: 'PUT'
                }
            });
});