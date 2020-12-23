<template>
  <div>
  <md-toolbar class="md-transparent" md-elevation="0">
    Menü
  </md-toolbar>

  <md-list>
    <md-list-item>
      <md-icon>move_to_inbox</md-icon>
      <span class="md-list-item-text">Inbox</span>
    </md-list-item>

    <md-list-item>
      <md-icon>send</md-icon>
      <span class="md-list-item-text">Sent Mail</span>
    </md-list-item>

    <md-list-item>
      <md-icon>delete</md-icon>
      <span class="md-list-item-text">Trash</span>
    </md-list-item>

    <md-list-item>
      <md-icon>error</md-icon>
      <span class="md-list-item-text">Spam</span>
    </md-list-item>
  </md-list>
    <md-button @click="logout" class="md-raised md-accent">Logout</md-button>
  </div>
</template>

<script>
import authenticationService from "@/authenticationService";
export default {
  name: 'Sidebar',
  props: {},
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

