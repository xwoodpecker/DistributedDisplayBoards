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
    {
        path: '/board/:id',
        name: 'board',
        component: () => import('./components/Board/BoardDetail.vue'),
        meta: {
            requiresAuth: true
        }
    },
];
