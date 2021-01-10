<template>
   <md-card>
      <md-card-header>
        <md-avatar>
          <md-icon>desktop_windows</md-icon>
        </md-avatar>

        <div class="md-title">{{ title }}</div>
        <div class="md-subhead">{{ location }}</div>
      </md-card-header>

      <md-card-media>
        <img src="/assets/examples/card-image-1.jpg" alt="Board Thumbnail">
      </md-card-media>

      <md-card-content>
        Active messages: {{ activeMessages }}
      </md-card-content>

      <md-card-actions>
        <md-button>Manage</md-button>
      </md-card-actions>
    </md-card>
</template>

<script>
export default {
  name: "BoardMaster",
  props: {
    title: String,
    location: String,
    activeMessages: Number,
    status: String //todo: make enum
  },
    data() {
    return {

    };
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
