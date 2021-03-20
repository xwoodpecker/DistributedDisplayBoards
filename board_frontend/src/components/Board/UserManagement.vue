<template>
  <div>
    <md-button @click="adduser = !adduser" v-if="adduser" class="md-dense md-primary">zurück</md-button>
    <md-button @click="adduser = !adduser" v-if="!adduser" class="md-dense md-raised md-primary">Nutzer hinzufügen</md-button>
    <md-list v-if="!adduser" class="md-double-line userlist">
      <md-list-item>
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
          <span>Ali Connors</span>
          <span>Brunch this weekend?</span>
          <p>I'll be in your neighborhood doing errands this week. Do you want to meet?</p>
        </div>

        <md-button class="md-icon-button md-list-action">
          <md-icon class="md-primary">delete</md-icon>
        </md-button>
      </md-list-item>
      <md-list-item>
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
          <span>Ali Connors</span>
          <span>Brunch this weekend?</span>
          <p>I'll be in your neighborhood doing errands this week. Do you want to meet?</p>
        </div>

        <md-button class="md-icon-button md-list-action">
          <md-icon class="md-primary">delete</md-icon>
        </md-button>
      </md-list-item>
    </md-list>
    <md-list v-if="adduser" class="md-double-line userlist all">
      <md-list-item>
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
          <span>Test Nutzer</span>
          <span>Brunch this weekend?</span>
          <p>I'll be in your neighborhood doing errands this week. Do you want to meet?</p>
        </div>

        <md-button class="md-icon-button md-list-action">
          <md-icon class="md-primary">add</md-icon>
        </md-button>
      </md-list-item>
      <md-list-item>
        <md-avatar>
          <img src="https://placeimg.com/40/40/people/1" alt="People">
        </md-avatar>

        <div class="md-list-item-text">
          <span>Ali Connors</span>
          <span>Brunch this weekend?</span>
          <p>I'll be in your neighborhood doing errands this week. Do you want to meet?</p>
        </div>

        <md-button class="md-icon-button md-list-action">
          <md-icon class="md-primary">add</md-icon>
        </md-button>
      </md-list-item>
    </md-list>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import {VueEditor} from "vue2-editor";
import Chrome from "vue-color/src/components/Chrome"
import Datepicker from 'vuejs-datepicker';
import UserManagement from "@/components/Board/UserManagement"

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker
  },
  props: {
    boardId: {
      type: Number,
    },
  },
  data() {
    return {
        adduser: false,
        groups: this.$store.getters.getGroups,
        group: groups.find(group => group.board.id == this.board)
    };
  },
  methods: {
    
  },
  computed: {},
  created() {
    this.boardId = this.$route.params.id;
  },
  mounted() {
    console.log(this.groups)
    console.log(this.group)
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
