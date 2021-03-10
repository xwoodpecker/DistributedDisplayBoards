export const ENV = {
    developerMode: false,
    environment: "development",
    baseUrl: "http://localhost:8000/",
    endpoints: {
        'login' : 'users/login',
        'boards': 'boards',
        'userboards' : 'users/%s/boards',
    }
}
