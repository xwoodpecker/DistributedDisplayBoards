import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'

Vue.use(VueRouter);
Vue.use(Vuex);

const routes = [
    {
        path: '/',
        name: 'dashboard',
        component: () => import('./App.vue'),
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

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isLoggedIn) {
            next({name: 'login'})
        } else {
            next() // go to wherever I'm going
        }
    } else {
        next() // does not require auth, make sure to always call next()!
    }
});

Vue.config.productionTip = false;

const store = new Vuex.Store({
    state: {
        user: null
    },
    mutations: {
        login(state, user) {
            state.user = user;
        },
        logout(state) {
            state.user = null;
        },
    },
    getters: {
        isLoggedIn: state => {
            return state.user != null;
        }
    }
});

new Vue({
    render: h => h(App),
    store,
    router,
}).$mount('#app');
