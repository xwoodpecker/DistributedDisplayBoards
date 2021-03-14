<template>
  <div ref="displayContainer" class="display-container">
    <agile ref="carousel" v-if="messages" v-bind:options="carouselOptions">
      <div v-for="message in messages" :key="message.id">
        <div
          :style="{
            height: height,
            'background-color': message.backgroundColor,
          }"
          class="message-container"
        >
          <div class="message-content" v-html="message.content"></div>
        </div>
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
export default {
  name: "BoardDisplay",
  props: {
    boardId: {
      type: Number,
    },
    autoStart: {
      type: Boolean,
      default: true,
    },
  },
  components: {},
  data() {
    return {
      height: "500px",
      carouselOptions: {
        dots: false,
        navButtons: false,
        rtl: true,
        speed: 1000,
      },
      amount: 0,
      currentSlide: 0,
      animation: undefined,
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
    messages: function () {
      return this.$store.getters.messages(this.boardId);
    }
  },
  created() {
  },
  mounted() {
    //make sure to fill available height. necessary due to limitations with vue-agile
    //todo add event listener to displayContainer, execute resize of this.height as its size changes
    this.height = this.$refs.displayContainer.clientHeight - 5 + "px";
    this.$watch("$refs.displayContainer.clientHeight", (new_value) => {
      this.height = new_value - 5 + "px";
    });
    this.$watch("$refs.carousel.currentSlide", (new_value) => {
      this.currentSlide = new_value;
      this.startAnimation(this.messages[new_value].duration);
    });
    if (this.autoStart) this.start();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.display-container {
  height: 100%;
}

.message-container {
  width: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
}
.message-content {
  width: 100%;
}
</style>
