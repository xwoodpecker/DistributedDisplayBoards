<template>
  <div class="md-layout md-gutter md-alignment-center card-container" @click="displayBoard($props.id)">
   <md-card class="singleBoard">
      <md-card-header>
        <md-avatar>
          <md-icon>desktop_windows</md-icon>
        </md-avatar>

        <div class="md-title">{{ title }}</div>
        <div class="md-subhead">{{ location }}</div>
      </md-card-header>

      <md-card-media class="media-container">
        <BoardDisplay
          v-if="this.id"
          v-bind:boardId="parseInt(this.id)"
        ></BoardDisplay>
      </md-card-media>

      <md-card-content v-if="messages">
        Active messages: {{ messages.length }}
      </md-card-content>

      <md-card-actions>
        <md-button @click.stop="openBoard($props.id)">Bearbeiten</md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script>
import BoardDisplay from "@/components/Board/BoardDisplay";
import screenfull from "screenfull";

export default {
  name: "BoardMaster",
  components: {
    BoardDisplay,
  },
  props: {
    title: String,
    location: String,
    messages: Array,
    id: Number,
    status: String,
  },
  data() {
    return {};
  },
  methods: {
    openBoard(boardId) {
      this.$router.push({ name: "board", params: { id: boardId } });
    },
    displayBoard(boardId) {
      if (screenfull.isEnabled) {
        screenfull.request(undefined, {navigationUI: 'hide'});
      }
      this.$router.push({ name: "screen", params: { id: boardId } });
    },
  },
  computed: {},
  created() {},
  mounted() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.media-container {
  max-width: 100%;
  height: 10vh;
}
.card-container {
  width: fit-content;
  display: inline;
}

.singleBoard {
  &:hover {
     box-shadow: 0 0 11px rgba(33,33,33,.2);
     cursor: pointer;
   }
}
</style>
