# ApiDocumentation.GroupsApi

All URIs are relative to *http://localhost:8000*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addGroupUsingPOST**](GroupsApi.md#addGroupUsingPOST) | **POST** /groups/ | Add a new group
[**addUserToGroupUsingPOST**](GroupsApi.md#addUserToGroupUsingPOST) | **POST** /groups/user/{id} | Add user to group
[**changeCoordinatorUsingPOST**](GroupsApi.md#changeCoordinatorUsingPOST) | **POST** /groups/coordinator/{id} | Change Coordinator
[**deleteGroupUsingDELETE**](GroupsApi.md#deleteGroupUsingDELETE) | **DELETE** /groups/{id} | Delete a group
[**getGroupUsingGET**](GroupsApi.md#getGroupUsingGET) | **GET** /groups/{id} | Get group by given id
[**getGroupsUsingGET**](GroupsApi.md#getGroupsUsingGET) | **GET** /groups/ | Get all groups

<a name="addGroupUsingPOST"></a>
# **addGroupUsingPOST**
> ResponseEntity addGroupUsingPOST(groupName, boardId, coordinatorId)

Add a new group

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
let groupName = "groupName_example"; // String | groupName
let boardId = 789; // Number | boardId
let coordinatorId = 789; // Number | coordinatorId

apiInstance.addGroupUsingPOST(groupName, boardId, coordinatorId, (error, data, response) => {
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
 **groupName** | **String**| groupName | 
 **boardId** | **Number**| boardId | 
 **coordinatorId** | **Number**| coordinatorId | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="addUserToGroupUsingPOST"></a>
# **addUserToGroupUsingPOST**
> ResponseEntity addUserToGroupUsingPOST(userId, id)

Add user to group

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
let userId = 789; // Number | userId
let id = 789; // Number | id

apiInstance.addUserToGroupUsingPOST(userId, id, (error, data, response) => {
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
 **userId** | **Number**| userId | 
 **id** | **Number**| id | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="changeCoordinatorUsingPOST"></a>
# **changeCoordinatorUsingPOST**
> ResponseEntity changeCoordinatorUsingPOST(newCoordinatorId, id)

Change Coordinator

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
let newCoordinatorId = 789; // Number | newCoordinatorId
let id = 789; // Number | id

apiInstance.changeCoordinatorUsingPOST(newCoordinatorId, id, (error, data, response) => {
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
 **newCoordinatorId** | **Number**| newCoordinatorId | 
 **id** | **Number**| id | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="deleteGroupUsingDELETE"></a>
# **deleteGroupUsingDELETE**
> ResponseEntity deleteGroupUsingDELETE(id)

Delete a group

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
let id = 789; // Number | id

apiInstance.deleteGroupUsingDELETE(id, (error, data, response) => {
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

<a name="getGroupUsingGET"></a>
# **getGroupUsingGET**
> ResponseEntity getGroupUsingGET(id)

Get group by given id

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
let id = 789; // Number | id

apiInstance.getGroupUsingGET(id, (error, data, response) => {
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

<a name="getGroupsUsingGET"></a>
# **getGroupsUsingGET**
> [Group] getGroupsUsingGET()

Get all groups

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.GroupsApi();
apiInstance.getGroupsUsingGET((error, data, response) => {
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

[**[Group]**](Group.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

