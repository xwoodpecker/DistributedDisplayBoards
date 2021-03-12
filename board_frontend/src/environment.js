export const ENV = {
    developerMode: false,
    environment: "development",
    baseUrl: "http://localhost:8000/",
    endpoints: {
        'login' : 'users/login',
        'boards': 'boards',
        'usergroups' : 'users/%s/groups',
        'userboards' : 'users/%s/boards',
        'users' : 'users/'
    }
}
