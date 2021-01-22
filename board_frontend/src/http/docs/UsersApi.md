# ApiDocumentation.UsersApi

All URIs are relative to *http://localhost:8000*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUserUsingPOST**](UsersApi.md#addUserUsingPOST) | **POST** /users/ | Add a new user
[**changeOwnPasswordUsingPOST**](UsersApi.md#changeOwnPasswordUsingPOST) | **POST** /users/password/own | Change the password of a user
[**changeSomeonesPasswordUsingPOST**](UsersApi.md#changeSomeonesPasswordUsingPOST) | **POST** /users/password/other | Change password of a specified user. Supervisor only
[**deleteUserUsingDELETE**](UsersApi.md#deleteUserUsingDELETE) | **DELETE** /users/{id} | Delete a user
[**getUserUsingGET**](UsersApi.md#getUserUsingGET) | **GET** /users/{id} | Get user with given id
[**getUsersUsingGET**](UsersApi.md#getUsersUsingGET) | **GET** /users/ | Get all users
[**replaceUserUsingPOST**](UsersApi.md#replaceUserUsingPOST) | **POST** /users/{id} | Replace a user with a new user

<a name="addUserUsingPOST"></a>
# **addUserUsingPOST**
> ResponseEntity addUserUsingPOST(userName, password, email, isSupervisor)

Add a new user

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let userName = "userName_example"; // String | userName
let password = "password_example"; // String | password
let email = "email_example"; // String | email
let isSupervisor = true; // Boolean | isSupervisor

apiInstance.addUserUsingPOST(userName, password, email, isSupervisor, (error, data, response) => {
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
 **userName** | **String**| userName | 
 **password** | **String**| password | 
 **email** | **String**| email | 
 **isSupervisor** | **Boolean**| isSupervisor | 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="changeOwnPasswordUsingPOST"></a>
# **changeOwnPasswordUsingPOST**
> ResponseEntity changeOwnPasswordUsingPOST(newPassword, opts)

Change the password of a user

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let newPassword = "newPassword_example"; // String | newPassword
let opts = { 
  'authenticated': true, // Boolean | 
  'authorities0Authority': "authorities0Authority_example", // String | 
  'credentials': null, // Object | 
  'details': null, // Object | 
  'name': "name_example", // String | 
  'principal': null // Object | 
};
apiInstance.changeOwnPasswordUsingPOST(newPassword, opts, (error, data, response) => {
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
 **newPassword** | **String**| newPassword | 
 **authenticated** | **Boolean**|  | [optional] 
 **authorities0Authority** | **String**|  | [optional] 
 **credentials** | [**Object**](.md)|  | [optional] 
 **details** | [**Object**](.md)|  | [optional] 
 **name** | **String**|  | [optional] 
 **principal** | [**Object**](.md)|  | [optional] 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="changeSomeonesPasswordUsingPOST"></a>
# **changeSomeonesPasswordUsingPOST**
> ResponseEntity changeSomeonesPasswordUsingPOST(opts)

Change password of a specified user. Supervisor only

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let opts = { 
  'username': "username_example", // String | username
  'newPassword': "newPassword_example" // String | newPassword
};
apiInstance.changeSomeonesPasswordUsingPOST(opts, (error, data, response) => {
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
 **username** | **String**| username | [optional] 
 **newPassword** | **String**| newPassword | [optional] 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="deleteUserUsingDELETE"></a>
# **deleteUserUsingDELETE**
> ResponseEntity deleteUserUsingDELETE(id)

Delete a user

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let id = 789; // Number | id

apiInstance.deleteUserUsingDELETE(id, (error, data, response) => {
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

<a name="getUserUsingGET"></a>
# **getUserUsingGET**
> ResponseEntity getUserUsingGET(id)

Get user with given id

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let id = 789; // Number | id

apiInstance.getUserUsingGET(id, (error, data, response) => {
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

<a name="getUsersUsingGET"></a>
# **getUsersUsingGET**
> ResponseEntity getUsersUsingGET()

Get all users

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
apiInstance.getUsersUsingGET((error, data, response) => {
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

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*

<a name="replaceUserUsingPOST"></a>
# **replaceUserUsingPOST**
> ResponseEntity replaceUserUsingPOST(id, opts)

Replace a user with a new user

### Example
```javascript
import ApiDocumentation from 'api_documentation';

let apiInstance = new ApiDocumentation.UsersApi();
let id = 789; // Number | id
let opts = { 
  'body': new ApiDocumentation.User() // User | 
};
apiInstance.replaceUserUsingPOST(id, opts, (error, data, response) => {
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
 **body** | [**User**](User.md)|  | [optional] 

### Return type

[**ResponseEntity**](ResponseEntity.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

