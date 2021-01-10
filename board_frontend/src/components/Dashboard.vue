<template>
  <div class="page-container">
    <md-app md-waterfall md-mode="overlap">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Dashboard</span>
        </div>
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <Sidebar></Sidebar>
      </md-app-drawer>

      <md-app-content>
        <div class="md-layout md-gutter md-alignment-center">
          <div
            v-for="board in mockBoards"
            v-bind:key="board.id"
            class="md-layout-item md-medium-size-33 md-small-size-50 md-xsmall-size-100"
          >
            <BoardMaster
              v-bind:title="board.title"
              v-bind:location="board.location"
              v-bind:activeMessages="board.activeMessages"
            >
            </BoardMaster>
          </div>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
import authenticationService from "../services/authenticationService";
import boardService from "../services/boardService";
import Sidebar from "@/components/Layout/Sidebar";
import BoardMaster from "@/components/Board/BoardMaster.vue";
export default {
  name: "Dashboard",
  props: {},
  components: {
    BoardMaster,
    Sidebar,
  },
  data() {
    return {
      user: this.$store.state.user,
      boardService: boardService,
      menuVisible: false,
      mockBoards: [
        { id: 1, title: "Board 1", location: "Mensa", activeMessages: 2 },
        {
          id: 2,
          title: "Board 2",
          location: "Meetingraum 1. Stock",
          activeMessages: 10,
        },
        { id: 3, title: "Board 3", location: "Foyer", activeMessages: 2 },
        {
          id: 4,
          title: "Board 4",
          location: "Meetingraum 2. Stock",
          activeMessages: 4,
        },
      ],
    };
  },
  methods: {
    logout() {
      authenticationService.logout();
      this.$router.push({ name: "login" });
    },
  },
  computed: {},
  created() {},
  mounted() {
    boardService.connect();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.md-app {
  height: 100vh;
  width: 100%;
  border: 1px solid rgba(#000, 0.12);
  .md-app-toolbar {
    background-color: #34495e;
  }
}

.md-drawer {
  width: 230px;
  max-width: calc(100vw - 125px);
}
</style>
