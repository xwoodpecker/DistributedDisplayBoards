const isProd = process.env.NODE_ENV === 'production';

export const ENV = {
    developerMode: !isProd,
    environment: "development",
    baseUrl: isProd? "/" : "http://localhost:8000/",
    stompBaseUrl: isProd? "/backend" : "http://localhost:8000/backend",
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
