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
    {
        path: '/board/:id',
        name: 'board',
        component: () => import('./components/Board/BoardDetail.vue'),
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/users',
        name: 'usermanagement',
        component: () => import('./components/Admin/Users.vue'),
        meta: {
            requiresAuth: true
        }
    },
];
