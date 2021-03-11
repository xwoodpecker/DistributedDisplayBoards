export const ENV = {
    developerMode: true,
    environment: "development",
    baseUrl: "http://localhost:8000/",
    endpoints: {
        'login' : 'users/login',
        'boards': 'boards',
        'userboards' : 'users/%s/boards',
        'users' : 'users/'
    }
}
