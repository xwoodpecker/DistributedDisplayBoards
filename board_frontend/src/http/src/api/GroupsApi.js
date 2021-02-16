/* eslint-disable */
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

import ApiClient from "../ApiClient";
import Group from '../model/Group';
import ResponseEntity from '../model/ResponseEntity';

/**
* Groups service.
* @module api/GroupsApi
* @version 1.0
*/
export default class GroupsApi {

    /**
    * Constructs a new GroupsApi. 
    * @alias module:api/GroupsApi
    * @class
    * @param {module:ApiClient} [apiClient] Optional API client implementation to use,
    * default to {@link module:ApiClient#instance} if unspecified.
    */
    constructor(apiClient) {
        this.apiClient = apiClient || ApiClient.instance;
    }

    /**
     * Callback function to receive the result of the addGroupUsingPOST operation.
     * @callback module:api/GroupsApi~addGroupUsingPOSTCallback
     * @param {String} error Error message, if any.
     * @param {module:model/ResponseEntity} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Add a new group
     * @param {module:api/GroupsApi~addGroupUsingPOSTCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link module:model/ResponseEntity}
     */
    addGroupUsingPOST(groupName, boardId, coordinatorId, callback) {
      let postBody = null;

      let pathParams = {
      };
      let queryParams = {
        'groupName': groupName,
        'boardId': boardId,
        'coordinatorId': coordinatorId
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ResponseEntity;

      return this.apiClient.callApi(
        '/groups/', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the addUserToGroupUsingPOST operation.
     * @callback module:api/GroupsApi~addUserToGroupUsingPOSTCallback
     * @param {String} error Error message, if any.
     * @param {module:model/ResponseEntity} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Add user to group
     * @param {module:api/GroupsApi~addUserToGroupUsingPOSTCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link module:model/ResponseEntity}
     */
    addUserToGroupUsingPOST(userId, id, callback) {
      let postBody = null;

      let pathParams = {
        'id': id
      };
      let queryParams = {
        'userId': userId
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ResponseEntity;

      return this.apiClient.callApi(
        '/groups/user/{id}', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the changeCoordinatorUsingPOST operation.
     * @callback module:api/GroupsApi~changeCoordinatorUsingPOSTCallback
     * @param {String} error Error message, if any.
     * @param {module:model/ResponseEntity} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Change Coordinator
     * @param {module:api/GroupsApi~changeCoordinatorUsingPOSTCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link module:model/ResponseEntity}
     */
    changeCoordinatorUsingPOST(newCoordinatorId, id, callback) {
      let postBody = null;

      let pathParams = {
        'id': id
      };
      let queryParams = {
        'newCoordinatorId': newCoordinatorId
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ResponseEntity;

      return this.apiClient.callApi(
        '/groups/coordinator/{id}', 'POST',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the deleteGroupUsingDELETE operation.
     * @callback module:api/GroupsApi~deleteGroupUsingDELETECallback
     * @param {String} error Error message, if any.
     * @param {module:model/ResponseEntity} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Delete a group
     * @param {module:api/GroupsApi~deleteGroupUsingDELETECallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link module:model/ResponseEntity}
     */
    deleteGroupUsingDELETE(id, callback) {
      let postBody = null;

      let pathParams = {
        'id': id
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ResponseEntity;

      return this.apiClient.callApi(
        '/groups/{id}', 'DELETE',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the getGroupUsingGET operation.
     * @callback module:api/GroupsApi~getGroupUsingGETCallback
     * @param {String} error Error message, if any.
     * @param {module:model/ResponseEntity} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Get group by given id
     * @param {module:api/GroupsApi~getGroupUsingGETCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link module:model/ResponseEntity}
     */
    getGroupUsingGET(id, callback) {
      let postBody = null;

      let pathParams = {
        'id': id
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = ResponseEntity;

      return this.apiClient.callApi(
        '/groups/{id}', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }
    /**
     * Callback function to receive the result of the getGroupsUsingGET operation.
     * @callback module:api/GroupsApi~getGroupsUsingGETCallback
     * @param {String} error Error message, if any.
     * @param {Array.<module:model/Group>} data The data returned by the service call.
     * @param {String} response The complete HTTP response.
     */

    /**
     * Get all groups
     * @param {module:api/GroupsApi~getGroupsUsingGETCallback} callback The callback function, accepting three arguments: error, data, response
     * data is of type: {@link Array.<module:model/Group>}
     */
    getGroupsUsingGET(callback) {
      let postBody = null;

      let pathParams = {
      };
      let queryParams = {
      };
      let headerParams = {
      };
      let formParams = {
      };

      let authNames = [];
      let contentTypes = [];
      let accepts = ['*/*'];
      let returnType = [Group];

      return this.apiClient.callApi(
        '/groups/', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }

}
