<template>
  <div class="page-container">
    <md-app md-waterfall md-mode="overlap">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Board {{ boardId }}</span>
        </div>
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <Sidebar></Sidebar>
      </md-app-drawer>

      <md-app-content class="board-content">
        <md-button @click='$router.push({ name: "dashboard" })' class="md-icon-button">
          <md-icon>home</md-icon>
        </md-button>
        <md-button class="md-icon-button" :class="{'md-raised md-primary' : this.messageActive}"
                   @click="setActiveTab('message')">
          <md-icon>edit</md-icon>
        </md-button>
        <md-button class="md-icon-button" :class="{'md-raised md-primary' : this.usersActive }"
                   @click="setActiveTab('users')">
          <md-icon>person</md-icon>
        </md-button>
        <div class="board-overview">
          <div class="board-form" v-if="this.messageActive">
            <vue-editor v-model="content"></vue-editor>
            <h1>{{content}}</h1>

            <!--            <div v-html="content"></div>
            &lt;!&ndash;          <BoardDetailForm></BoardDetailForm>&ndash;&gt;-->
          </div>
          <md-button class="md-icon-button sendbutton" :class="{'md-raised' : this.messageActive}"
                     @click="setActiveTab('message')">
            <md-icon>add</md-icon>
          </md-button>
          <div class="board-users" v-if="this.usersActive">
            Penis
          </div>
          <div class="board-preview"></div>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import {VueEditor} from "vue2-editor";

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
  },
  props: {},
  data() {
    return {
      boardId: null,
      board: null,
      menuVisible: false,
      usersActive: false,
      messageActive: true,
      content: ""
    };
  },
  methods: {
    setActiveTab(tab) {
      if (tab === 'message') {
        this.messageActive = true;
        this.usersActive = false;
      } else {
        this.messageActive = false;
        this.usersActive = true;
      }
    }
  },
  computed: {},
  created() {
    this.boardId = this.$route.params.id;

    //hier das passende Board anhand ID aus dem store holen und socket aufbauen für master
    console.log("Board Id is" + this.boardId)
  },
  mounted() {
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.board {
  &-content {
    position: relative;
    overflow: hidden;
    height: 100vh;
  }

  &-overview {
    height: 100vh;
  }

  &-preview {
    width: 50vw;
    height: 100vh;
    position: absolute;
    top: 0;
    right: 0;
    background-color: #ebebeb;
  }

  &-form {
    z-index: 187;
    position: absolute;
    left: 2%;
    top: 20%;
    max-width: 50vw;
    background-color: #fff;
    box-shadow: 0 2.8px 2.2px rgb(0 0 0 / 3%), 0 6.7px 5.3px rgb(0 0 0 / 5%), 0 12.5px 10px rgb(0 0 0 / 6%), 0 22.3px 17.9px rgb(0 0 0 / 7%), 0 41.8px 33.4px rgb(0 0 0 / 9%), 0 100px 80px rgb(0 0 0 / 12%);
  }
}

.sendbutton {
  position: absolute;
  top: 63%;
  z-index: 1877;
  left: 25.5%;
  background-color:  #B4D7A8 !important;
}

</style>
