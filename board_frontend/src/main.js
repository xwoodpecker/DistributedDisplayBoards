import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'

Vue.config.productionTip = false;
Vue.use(Vuex);

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
  }
});

new Vue({
  render: h => h(App),
  store: store,
}).$mount('#app');
