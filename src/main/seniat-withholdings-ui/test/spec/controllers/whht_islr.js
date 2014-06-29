'use strict';

describe('Controller: WhhtIslrCtrl', function () {

  // load the controller's module
  beforeEach(module('seniatWithholdingsUiApp'));

  var WhhtIslrCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    WhhtIslrCtrl = $controller('WhhtIslrCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
