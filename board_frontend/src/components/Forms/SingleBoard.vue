<template>
  <md-list-item>
    <md-avatar>
      <img src="https://placeimg.com/40/40/people/1" alt="People">
    </md-avatar>

    <div class="md-list-item-text">
      <span>{{ board.boardName }}</span>
      <span>{{ board.location}}</span>
    </div>
  </md-list-item>
</template>

<script>

export default {
name: 'SingleBoard',
props: ['board'],
components: {
},
data() {
return {
}
},
methods: {
},
computed: {},
created() {

},
mounted() {

}
}
</script>

<style lang="scss" scoped>

</style>
