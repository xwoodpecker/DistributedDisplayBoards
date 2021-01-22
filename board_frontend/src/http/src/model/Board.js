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

import ApiClient from '../ApiClient';

/**
* The Board model module.
* @module model/Board
* @version 1.0
*/
export default class Board {
    /**
    * Constructs a new <code>Board</code>.
    * @alias module:model/Board
    * @class
    */

    constructor() {
        
        
        
    }

    /**
    * Constructs a <code>Board</code> from a plain JavaScript object, optionally creating a new instance.
    * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
    * @param {Object} data The plain JavaScript object bearing properties of interest.
    * @param {module:model/Board} obj Optional instance to populate.
    * @return {module:model/Board} The populated <code>Board</code> instance.
    */
    static constructFromObject(data, obj) {
        if (data) {
            obj = obj || new Board();
                        
            
            if (data.hasOwnProperty('boardName')) {
                obj['boardName'] = ApiClient.convertToType(data['boardName'], 'String');
            }
            if (data.hasOwnProperty('id')) {
                obj['id'] = ApiClient.convertToType(data['id'], 'Number');
            }
        }
        return obj;
    }

    /**
    * @member {String} boardName
    */
    'boardName' = undefined;
    /**
    * @member {Number} id
    */
    'id' = undefined;




}
