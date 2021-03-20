export const ENV = {
    developerMode: false,
    environment: "development",
    baseUrl: "/",
    endpoints: {
        'login' : 'users/login',
        'boards': 'groups/',
        'usergroups' : 'users/%s/groups',
        'userboards' : 'users/%s/boards',
        'users' : 'users/',
        'boardmessages' : 'messages/board/%s',
        'message': 'messages/%s',
    }
}
