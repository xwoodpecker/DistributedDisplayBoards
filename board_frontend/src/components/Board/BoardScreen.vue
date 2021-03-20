<template>
  <div class="display-container">
    <BoardDisplay
      v-if="this.boardId"
      v-bind:boardId="this.boardId"
    ></BoardDisplay>
  </div>
</template>

<script>
import BoardDisplay from "@/components/Board/BoardDisplay";
export default {
  name: "BoardScreen",
  components: {
    BoardDisplay,
  },
  props: {},
  data() {
    return {
      boardId: null,
    };
  },
  computed: {},
  methods: {},
  created() {
    this.boardId = parseInt(this.$route.params.id);
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.display-container {
  width: 100vw;
  height: 100vh;
}
</style>
