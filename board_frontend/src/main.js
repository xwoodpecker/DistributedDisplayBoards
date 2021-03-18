import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import {routes} from './routes'
import createPersistedState from 'vuex-persistedstate'
import VueMaterial from 'vue-material'
import VueAgile from 'vue-agile'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import {ENV} from './environment'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.config.productionTip = false;
Vue.use(VueMaterial)
Vue.use(VueAgile)

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
});


router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isLoggedIn && !ENV.developerMode) {
            next({name: 'login'})
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
        authHeader: null,
        boards: [],
        userToEdit: null
    },
    mutations: {
        login(state, user) {
            state.user = user;
            console.log("user", state.user)
        },
        logout(state) {
            state.user = null;
            state.authHeader = null;
        },
        addBoards(state, boards) {
            state.boards = boards;
        },
        setMessages(state, board) {
            state.boards.find(oldBoard => board.id == oldBoard.id).messages = board.messages;
        },
        editUser(state, user) {
            state.userToEdit = user;
        },
        clearBoards(state) {
            state.boards = {}
        },
        setAuthHeader(state, auth) {
            state.authHeader = {'Authorization': 'Basic ' + window.btoa(auth.username + ':' + auth.password)};
            console.log("authHeader", state.authHeader)
        }
    },
    getters: {
        isLoggedIn: state => {
            return state.user != null;
        },
        boards: state => {
            return state.boards;
        },
        messages: state => (boardId) => {
            let board = state.boards.find(board => board.id == boardId);
            return board ? board.messages : undefined;
        },
        userToEdit: state => {
            return state.userToEdit
        },
        authHeader: state => {
            return state.authHeader
        },
        getUser: state => {
            return state.user
        }
    }
});


new Vue({
    render: h => h(App),
    store,
    router
}).$mount('#app');
