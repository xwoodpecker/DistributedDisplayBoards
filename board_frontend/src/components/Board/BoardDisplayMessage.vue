<template>
  <div>{{ message.id }}</div>
</template>

<script>
export default {
  name: "BoardDisplayMessage",
  props: {
    message: Object,
  },
  data() {
    return {};
  },
  methods: {},
  computed: {},
  created() {},
  mounted() {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
