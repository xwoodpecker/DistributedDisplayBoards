<template>
  <div>
    <md-list-item>
      <md-avatar>
        <img src="https://placeimg.com/40/40/people/1" alt="People">
      </md-avatar>

      <div class="md-list-item-text">
        <h2>{{ group.groupName }}</h2>
      </div>
    </md-list-item>
    <div class="board">
    <div class="board-description">
      <h3>Board: {{ group.board.boardName }}</h3>
      <span><md-icon>location_on</md-icon>{{ group.board.location }}</span>
      <span><md-icon>supervisor_account</md-icon>{{ group.users.length }} Mitglieder</span>
    </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'SingleBoard',
  props: ['group'],
  components: {},
  data() {
    return {}
  },
  methods: {},
  computed: {},
  created() {

  },
  mounted() {

  }
}
</script>

<style lang="scss" scoped>
.board {
  padding: 4px 16px 4px 16px;
  display: flex;
  justify-content: center;
  &-description {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    span {
      padding-left: 15px;
    }
  }
}
</style>
