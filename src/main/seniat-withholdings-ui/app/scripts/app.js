'use strict';

angular
  .module('seniatWithholdingsUiApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'angular-hal'
  ])
  .run(function($rootScope, halClient){
    $rootScope.apiRoot = halClient.$get('//api');
  })
  .config(function ($routeProvider, HateoasInterceptorProvider) {
    HateoasInterceptorProvider.transformAllResponses();
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/whht/iva', {
        templateUrl: 'views/whht_iva.html',
        controller: 'WhhtIvaCtrl'
      })
      .when('/whht/islr', {
        templateUrl: 'views/whht_islr.html',
        controller: 'WhhtIslrCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
