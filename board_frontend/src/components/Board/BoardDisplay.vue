<template>
  <div>
    <agile ref="carousel" v-if="messages" v-bind:options="carouselOptions">
      <div v-for="message in messages" :key="message.id">
        <BoardDisplayMessage v-bind:message="message" />
        <!-- todo (low prio) make sure currently not displayed progress-bars are not constantly being updated -->
        <md-progress-bar
          md-mode="determinate"
          :md-value="amount"
        ></md-progress-bar>
      </div>
    </agile>
  </div>
</template>

<script>
import gsap from "gsap";
import BoardDisplayMessage from "@/components/Board/BoardDisplayMessage.vue";
export default {
  name: "BoardDisplay",
  props: {
    //messages: Array,
    autoStart: {
      type: Boolean,
      default: true,
    },
  },
  components: {
    BoardDisplayMessage,
  },
  data() {
    return {
      carouselOptions: {
        dots: false,
        navButtons: false,
        rtl: true,
        speed: 1000,
      },
      amount: 0,
      currentSlide: 0,
      animation: undefined,
      messages: [
        {
          id: 1,
          content: "This is a message!",
          duration: 5,
        },
        {
          id: 2,
          content: "This is a different message!",
          duration: 5,
        },
      ],
    };
  },
  methods: {
    start() {
      this.startAnimation(
        this.messages[this.$refs.carousel.currentSlide].duration
      );
    },
    play() {
      if (this.animation) this.animation.play();
      else this.start();
    },
    pause() {
      if (this.animation) this.animation.pause();
    },
    goto() {},
    next() {
      this.$refs.carousel.goToNext();
    },
    startAnimation(duration) {
      if (this.animation) this.animation.kill();
      this.amount = 0;
      this.animation = gsap.fromTo(
        this.$data,
        { amount: 0 },
        {
          ease: "none",
          duration: duration,
          amount: 100,
        }
      );
      this.animation.then(() => {
        this.next();
      });
    },
  },
  computed: {
    animatedAmount: function () {
      return this.tweenedNumber.toFixed(0);
    },
  },
  created() {},
  mounted() {
    this.$watch("$refs.carousel.currentSlide", (new_value) => {
      this.currentSlide = new_value;
      this.startAnimation(this.messages[new_value].duration);
    });
    if (this.autoStart) this.start();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
