# ApiDocumentation.BoardsApi

All URIs are relative to *http://localhost:8000*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addBoardUsingPOST**](BoardsApi.md#addBoardUsingPOST) | **POST** /boards/ | Add a new board
[**deleteBoardUsingDELETE**](BoardsApi.md#deleteBoardUsingDELETE) | **DELETE** /boards/{id} | Delete a board
[**getBoardUsingGET**](BoardsApi.md#getBoardUsingGET) | **GET** /boards/{id} | Get board by given id
[**getBoardsUsingGET**](BoardsApi.md#getBoardsUsingGET) | **GET** /boards/ | Get all boards
[**replaceBoardUsingPOST**](BoardsApi.md#replaceBoardUsingPOST) | **POST** /boards/{id} | Change board name

<a name="addBoardUsingPOST"></a>
# **addBoardUsingPOST**
> ResponseEntity addBoardUsingPOST(opts)

Add a new board

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.BoardsApi();
let opts = { 
  'body': new ApiDocumentation.Board() // Board | 
};
apiInstance.addBoardUsingPOST(opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Board**](Board.md)|  | [optional] 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

<a name="deleteBoardUsingDELETE"></a>
# **deleteBoardUsingDELETE**
> ResponseEntity deleteBoardUsingDELETE(id)

Delete a board

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.BoardsApi();
let id = 789; // Number | id

apiInstance.deleteBoardUsingDELETE(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Number**| id | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getBoardUsingGET"></a>
# **getBoardUsingGET**
> ResponseEntity getBoardUsingGET(id)

Get board by given id

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.BoardsApi();
let id = 789; // Number | id

apiInstance.getBoardUsingGET(id, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Number**| id | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="getBoardsUsingGET"></a>
# **getBoardsUsingGET**
> [Board] getBoardsUsingGET()

Get all boards

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.BoardsApi();
apiInstance.getBoardsUsingGET((error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**[Board]**](Board.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="replaceBoardUsingPOST"></a>
# **replaceBoardUsingPOST**
> ResponseEntity replaceBoardUsingPOST(id, opts)

Change board name

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.BoardsApi();
let id = 789; // Number | id
let opts = { 
  'body': new ApiDocumentation.Board() // Board | 
};
apiInstance.replaceBoardUsingPOST(id, opts, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Number**| id | 
 **body** | [**Board**](Board.md)|  | [optional] 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

