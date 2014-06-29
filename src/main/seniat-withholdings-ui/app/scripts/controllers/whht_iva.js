'use strict';

angular.module('seniatWithholdingsUiApp')
  .controller('WhhtIvaCtrl', function ($window, $scope, $timeout, halClient) {
    
    var searchTimeout;
    $scope.$watch('search', function(value){
      $timeout.cancel(searchTimeout);
      searchTimeout = $timeout(load, 300);
    }, true);

    $scope.save = function(){
      if($scope.withholding.$invalid) return;
      
      return $scope.apiRoot.then(function(apiRoot){
        return apiRoot.$post('withholdings', null, $scope.withholding);
      }).then(load);
    };

    function load(){
      var search = $scope.search;
      var promise;

      if(search){
        promise = $scope.apiRoot.then(function(apiRoot){
          return apiRoot.$get('withholdings', {
            search: search
          });
        });
      
      } else {
        promise = $scope.apiRoot.then(function(apiRoot){
          return apiRoot.$get('withholdings');
        });
      }

      return promise.then(function(withholdings){
        $scope.withholdings = withholdings;
        $scope.withholding.$setPristine();
        delete $scope.withholding;
      });
    };
  });
