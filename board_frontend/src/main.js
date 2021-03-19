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
import 'toastr/toastr.scss'
import Toastr from 'toastr'
import BoardService from './services/boardService'

Vue.use(VueRouter);
Vue.use(Vuex);
Vue.config.productionTip = false;
Vue.use(VueMaterial)
Vue.use(VueAgile)

const router = new VueRouter({
    mode: 'history',
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
        users: [],
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
            for(let board of boards){
                if(!state.boards.find(oldBoard => board.id == oldBoard.id)){
                    state.boards.push(board);
                }
            }
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
        },
        setUsers(state, users) {
            state.users = users;
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
        },
        getUsers: state => {
            return state.users
        },
        isSupervisor: state => {
            let isSupervisor = false;
            return state.user.roles.find( role => role.name === 'SUPERVISOR')
        }
    }
});

Vue.prototype.$boardService = new BoardService();
Vue.prototype.$toastr = Toastr;

new Vue({
    render: h => h(App),
    store,
    router
}).$mount('#app');
