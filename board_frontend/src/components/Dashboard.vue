<template>
  <div class="page-container">
    <md-app md-waterfall md-mode="overlap">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Dashboard von {{$store.getters.getUser.userName}}</span>
        </div>
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <Sidebar></Sidebar>
      </md-app-drawer>

      <md-app-content v-if="boards && boards.length">
        <div class="md-layout md-gutter board-overview">
          <div
              v-for="board in boards"
              v-bind:key="board.id"
              class="md-layout-item md-size-25 md-medium-size-33 md-small-size-50 md-xsmall-size-100"
          >
            <BoardMaster
                :title="board.boardName"
                :location="board.location"
                :messages="board.messages"
                :id="board.id"
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
import Sidebar from "@/components/Layout/Sidebar";
import BoardMaster from "@/components/Board/BoardMaster.vue";
import boardsapi from "@/http/boardsapi";
import BoardService from '@/services/boardService'

export default {
  name: "Dashboard",
  props: {},
  components: {
    BoardMaster,
    Sidebar,
  },
  data() {
    return {
      user: this.$store.getters.user,
      boards: this.$store.getters.boards,
      menuVisible: false,
    };
  },
  methods: {},
  computed: {},
  created() {
  },
  mounted() {
    boardsapi.getUserGroups(this.$store.state.user.id).then(groups => {
      if (groups) {
        let boards = [];
        for (let group of groups) {
          if (group.board) {
            boards.push(group.board);
          }
        }
        this.$store.commit('setGroups', groups);
        this.$store.commit('addBoards', boards);
        this.$boardService = new BoardService();
      }
    });
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.md-app {
  height: 100vh;
  width: 100%;
  border: 1px solid rgba(#000, 0.12);

}

.md-drawer {
  width: 230px;
  max-width: calc(100vw - 125px);
}


</style>
