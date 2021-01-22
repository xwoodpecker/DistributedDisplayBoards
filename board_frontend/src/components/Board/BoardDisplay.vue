<template>
    <agile ref="carousel" v-bind:options="carouselOptions">
        <div class="slide">
            <h3>slide 1</h3>
        </div>
        
        <div class="slide">
            <h3>slide n</h3>
        </div>
    </agile>
</template>

<script>
export default {
  name: "BoardDisplay",
  props: {
    messages: Array,
  },
    data() {
    return {
      carouselOptions: {
        dots: false,
        navButtons: false,
        speed: 300, //default
        timing: "ease", //default
      }
    };
  },
  methods: {
    play(){

    },
    pause(){

    },
    goto(){

    }
  },
  computed: {},
  created() {},
  mounted() {
    setInterval(() => {
      this.$refs.carousel.goToNext()
    }, 1000)
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
