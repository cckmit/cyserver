
//简历状态
myApp.controller('jianlizhuangtaiViewCtrls', function($scope,$http,$stateParams,$state) {
    $state.go('jianlizhuangtai.jianli',{'id':'1'});
    $scope.jselect1=true;

    //顶部选项卡的切换
    $scope.jchangeTabs = function(index){
        if(index==1){
            $scope.jselect1=true;
            $scope.jselect2=false;
            $scope.jselect3=false;
            $scope.jselect4=false;
            $scope.jselect5=false;
        }else if(index==2){
            $scope.jselect1=false;
            $scope.jselect2=true;
            $scope.jselect3=false;
            $scope.jselect4=false;
            $scope.jselect5=false;
        }else if(index==3){
            $scope.jselect1=false;
            $scope.jselect2=false;
            $scope.jselect3=true;
            $scope.jselect4=false;
            $scope.jselect5=false;
        }else if(index==4){
            $scope.jselect1=false;
            $scope.jselect2=false;
            $scope.jselect3=false;
            $scope.jselect4=true;
            $scope.jselect5=false;
        }else if(index==5){
            $scope.jselect1=false;
            $scope.jselect2=false;
            $scope.jselect3=false;
            $scope.jselect4=false;
            $scope.jselect5=true;
        }
    }
})

myApp.controller('jianliViewCtrls', function($scope,$http,$stateParams,$state) {
    console.log($stateParams.id);
    
})