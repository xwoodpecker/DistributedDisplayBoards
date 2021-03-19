<template>
  <div>
  <md-list>
    <md-list-item @click='$router.push({ name: "dashboard" })'>
      <md-icon>dashboard</md-icon>
      <span class="md-list-item-text">Dashboard</span>
    </md-list-item>

    <md-list-item @click='$router.push({ name: "usermanagement" })'>
      <md-icon>person</md-icon>
      <span class="md-list-item-text">Benutzerverwaltung</span>
    </md-list-item>

    <md-list-item v-if="$store.getters.isSupervisor || $store.getters.hasRoleCoordinator" @click='$router.push({ name: "boardmanagement" })'>
      <md-icon>filter_frames</md-icon>
      <span class="md-list-item-text">Boardverwaltung</span>
    </md-list-item>
  </md-list>
  <md-button @click="logout" class="md-raised md-accent">Logout</md-button>
  </div>
</template>

<script>
import authenticationService from "@/services/authenticationService";
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

