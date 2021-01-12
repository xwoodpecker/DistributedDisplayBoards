import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import { routes } from './routes'
import createPersistedState from 'vuex-persistedstate'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import { ENV } from './environment'
import BoardService from './services/boardService'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.config.productionTip = false;
Vue.use(VueMaterial)

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isLoggedIn && !ENV.developerMode) {
            next({ name: 'login' })
        } else {
            next() // go to wherever I'm going
        }
    } else {
        next() // does not require auth, make sure to always call next()!
    }
});

export const store = new Vuex.Store({
    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })],
    state: {
        user: null,
        boards: {}
    },
    mutations: {
        login(state, user) {
            state.user = user;
        },
        logout(state) {
            state.user = null;
        },
        addBoards(state, boards){
            for(let board of boards)
                state.boards[board.id] = board;
        },
        clearBoards(state) {
            state.boards = {}
        }
    },
    getters: {
        isLoggedIn: state => {
            return state.user != null;
        },
        boards: state => {
            return state.boards;
        }
    }
});

Vue.prototype.$boardService = new BoardService();

new Vue({
    render: h => h(App),
    store,
    router,
}).$mount('#app');
