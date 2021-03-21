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
        path: '/screen/:id',
        name: 'screen',
        component: () => import('./components/Board/BoardScreen.vue'),
        meta: {
            requiresAuth: true
        }
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
    {
        path: '/boards',
        name: 'boardmanagement',
        component: () => import('./components/Admin/Boards.vue'),
        meta: {
            requiresAuth: true
        }
    },
];
