<template>
  <div class="page-container">
    <md-app md-waterfall md-mode="overlap">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">{{}}</span>
        </div>
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <Sidebar></Sidebar>
      </md-app-drawer>

      <md-app-content>
        <div class="md-layout md-gutter md-alignment-center board-overview">
          <BoardDetailForm></BoardDetailForm>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
import BoardDetailForm from "@/components/Board/BoardDetailForm";
export default {
  name: "BoardDetail",
  components: {
    BoardDetailForm
  },
  props: {
    
  },
    data() {
    return {
        boardId: null,
        board: null,
    };
  },
  methods: {},
  computed: {},
  created() {
    this.boardId = this.$route.params.id;

    //hier das passende Board anhand ID aus dem store holen und socket aufbauen für master
    console.log("Board Id is" + this.boardId)
  },
  mounted() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
