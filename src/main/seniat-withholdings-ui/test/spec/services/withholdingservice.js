'use strict';

describe('Service: withholdingService', function () {

  // load the service's module
  beforeEach(module('seniatWithholdingsUiApp'));

  // instantiate service
  var withholdingService;
  beforeEach(inject(function (_withholdingService_) {
    withholdingService = _withholdingService_;
  }));

  it('should do something', function () {
    expect(!!withholdingService).toBe(true);
  });

});
