<template>
  <div>
    <md-button @click="adduser = !adduser" v-if="adduser" class="md-dense md-primary">zurück</md-button>
    <md-button @click="adduser = !adduser" v-if="!adduser" class="md-dense md-raised md-primary">Nutzer hinzufügen</md-button>
    <md-list v-if="!adduser" class="md-double-line userlist">
      <md-list-item v-for="user in this.users" :key="user.id">
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
          <span>{{user.userName}}</span>
          <span>{{user.email}}</span>
        </div>

        <md-button @click="removeUser(user.id)" class="md-icon-button md-list-action">
          <md-icon class="md-primary">delete</md-icon>
        </md-button>
      </md-list-item>
    </md-list>
    <md-list v-if="adduser" class="md-double-line userlist all">
      <md-list-item v-for="user in this.allUsers" :key="user.id">
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
       <span>{{user.userName}}</span>
          <span>{{user.email}}</span>
        </div>

        <md-button @click="addUser(user.id)" class="md-icon-button md-list-action">
          <md-icon class="md-primary">add</md-icon>
        </md-button>
      </md-list-item>
    </md-list>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import userapi from "@/http/userapi";
import groupsapi from "@/http/groupsapi";
import {VueEditor} from "vue2-editor";
import Chrome from "vue-color/src/components/Chrome"
import Datepicker from 'vuejs-datepicker';

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker
  },
  data() {
    return {
        adduser: false,
        groups: this.$store.getters.getGroups,
        allUsers: [],
        users: [],
    };
  },
  methods: {
    async updateUsers() {
      let allUsers = await userapi.getUsers()//.then(result => this.allUsers = result);
      let users = await groupsapi.getGroupUsers(this.group.id)//.then(result => this.users = result);
      this.allUsers = allUsers.filter(user => !users.find(user2 => user2.id == user.id));
      this.users = users;
    },
    addUser(id){
      groupsapi.addUser(id, this.group.id).then(result => {
        this.updateUsers();
      })
    },
    removeUser(id){
      groupsapi.removeUser(id, this.group.id).then(result => {
        this.updateUsers();
      })
    }
  },
  computed: {
    group(){return this.groups.find(group => group.board.id == this.boardId)},
  },
  created() {
    this.boardId = this.$route.params.id;
    this.updateUsers();
  },
  mounted() {
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

button {
  margin-top: 25px;
}
.userlist {
  height: 100vh;
  width: 50%;
  margin: 25px 25px 15px 0;
  display: flex;
  flex-direction: column;
}
</style>
