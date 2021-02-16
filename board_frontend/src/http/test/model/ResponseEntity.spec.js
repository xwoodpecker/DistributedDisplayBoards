/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 */
(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD.
    define(['expect.js', '../../src/index'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    factory(require('expect.js'), require('../../src/index'));
  } else {
    // Browser globals (root is window)
    factory(root.expect, root.ApiDocumentation);
  }
}(this, function(expect, ApiDocumentation) {
  'use strict';

  var instance;

  beforeEach(function() {
    instance = new ApiDocumentation.ResponseEntity();
  });

  var getProperty = function(object, getter, property) {
    // Use getter method if present; otherwise, get the property directly.
    if (typeof object[getter] === 'function')
      return object[getter]();
    else
      return object[property];
  }

  var setProperty = function(object, setter, property, value) {
    // Use setter method if present; otherwise, set the property directly.
    if (typeof object[setter] === 'function')
      object[setter](value);
    else
      object[property] = value;
  }

  describe('ResponseEntity', function() {
    it('should create an instance of ResponseEntity', function() {
      // uncomment below and update the code to test ResponseEntity
      //var instane = new ApiDocumentation.ResponseEntity();
      //expect(instance).to.be.a(ApiDocumentation.ResponseEntity);
    });

    it('should have the property body (base name: "body")', function() {
      // uncomment below and update the code to test the property body
      //var instane = new ApiDocumentation.ResponseEntity();
      //expect(instance).to.be();
    });

    it('should have the property statusCode (base name: "statusCode")', function() {
      // uncomment below and update the code to test the property statusCode
      //var instane = new ApiDocumentation.ResponseEntity();
      //expect(instance).to.be();
    });

    it('should have the property statusCodeValue (base name: "statusCodeValue")', function() {
      // uncomment below and update the code to test the property statusCodeValue
      //var instane = new ApiDocumentation.ResponseEntity();
      //expect(instance).to.be();
    });

  });

}));