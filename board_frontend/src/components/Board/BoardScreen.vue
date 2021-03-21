<template>
  <div>
  <div class="closer" @click="openDashboard">
    <md-icon class="md-size-2x">close</md-icon>
  </div>
  <div class="display-container">
    <BoardDisplay
      v-if="this.boardId"
      v-bind:boardId="this.boardId"
    ></BoardDisplay>
  </div>
  </div>
</template>

<script>
import BoardDisplay from "@/components/Board/BoardDisplay";
import screenfull from "screenfull";
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
  methods: {
    openDashboard(){
      if (screenfull.isEnabled) screenfull.exit();
      this.$router.push("/")
    }
  },
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

.closer {
  border-radius: 50%;
  cursor: pointer;
  position: absolute;
  top: 5%;
  right: 5%;
  z-index: 10;
  box-shadow: 0 0 11px rgba(33,33,33,.2);
  &:hover {
    box-shadow: 0 0 11px rgba(33,33,33,.8);
  }
}
</style>
