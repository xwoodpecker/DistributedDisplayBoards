<template>
  <md-list-item>
    <md-avatar>
      <img src="https://placeimg.com/40/40/people/1" alt="People">
    </md-avatar>

    <div class="md-list-item-text">
      <span>{{ user.userName }}</span>
      <span>{{ user.email}}</span>
    </div>
  </md-list-item>
</template>

<script>

export default {
name: 'SingleUser',
props: ['user'],
components: {
},
data() {
return {
}
},
methods: {
logout() {
authenticationService.logout();
this.$router.push({name: 'login'});
}
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
