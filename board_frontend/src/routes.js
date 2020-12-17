export const routes = [
    {
        path: '/',
        name: 'dashboard',
        component: () => import('./components/Dashboard.vue'),
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('./components/Login.vue')
    },
];
