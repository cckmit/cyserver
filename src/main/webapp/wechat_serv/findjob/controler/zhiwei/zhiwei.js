/**
 * Created by mcz on 2017/6/2.
 */
myApp.controller('zhiweicenterViewCtrls', function($scope,$http,$stateParams) {
    console.log($stateParams.id);
    gangweicenter($scope,$http,$stateParams.id);
})

//获取岗位详情
function gangweicenter($scope,$http,id){
    B.ready(function(){
        $http({
            method: 'POST',
            url: B.serverUrl+'/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action',
            data:{
                jsonStr:'{"command": "623","content": {"id": "'+id+'"}}'
            },
            dataType: 'json'
        }).then(function successCallback(response) {
            if(response.data.obj && response.data.obj!=''){
                $scope.zwcenter=response.data.obj;
            }

            // console.log(response);
        }, function errorCallback(response) {
            //alert('22:'+JSON.stringify(response));
        });

    })
}

