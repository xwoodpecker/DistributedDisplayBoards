<template>
  <div class="md-layout md-gutter md-alignment-center card-container">
   <md-card>
      <md-card-header>
        <md-avatar>
          <md-icon>desktop_windows</md-icon>
        </md-avatar>

        <div class="md-title">{{ title }}</div>
        <div class="md-subhead">{{ location }}</div>
      </md-card-header>

      <md-card-media class="media-container">
        <BoardDisplay></BoardDisplay>
      </md-card-media>

      <md-card-content>
        Active messages: {{ messages.length }}
      </md-card-content>

      <md-card-actions>
        <md-button @click="openBoard($props.id)">Manage</md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script>
import BoardDisplay from "@/components/Board/BoardDisplay";

export default {
  name: "BoardMaster",
  components: {
    BoardDisplay
  },
  props: {
    title: String,
    location: String,
    messages: Array,
    id: Number,
    status: String //todo: make enum
  },
    data() {
    return {

    };
  },
  methods: {
    openBoard(boardId){
      this.$router.push({ name: 'board', params: { id: boardId }})
    }
  },
  computed: {},
  created() {},
  mounted() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.media-container {
  max-width: 100%;
  height: 10vh;
}
.card-container {
  width: fit-content;
  display: inline;
}
</style>
