export const routes = [
    {
        path: '/',
        name: 'dashboard',
        //todo change back to Dashboard
        //component: () => import('./components/Board/BoardDisplay.vue'),
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
