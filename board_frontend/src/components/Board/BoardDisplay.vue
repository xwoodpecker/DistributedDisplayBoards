<template>
  <div ref="displayContainer" class="display-container">
    <agile
      ref="carousel"
      v-bind:options="carouselOptions"
      :key="messages.length"
      v-if="messages"
      @after-change="handleNextSlide($event.currentSlide)"
    >
      <div v-for="message in messages" :key="message.id">
        <div
          :style="{
            height: height,
            'background-color': message.bgColor,
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
      messages: this.$store.getters.messages(this.boardId),
    };
  },
  methods: {
    start() {
      //todo test
      if (this.messages && this.messages.length > 1) {
        this.startAnimation(
          this.messages[this.$refs.carousel.currentSlide].displayTime
        );
      }
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
    startAnimation(displayTime) {
      if (this.animation) this.animation.kill();
      this.amount = 0;
      this.animation = gsap.fromTo(
        this.$data,
        { amount: 0 },
        {
          ease: "none",
          duration: displayTime,
          amount: 100,
        }
      );

      this.animation.then(() => {
        this.next();
      });
    },
    handleNextSlide(slide){
      console.log(slide);
      this.currentSlide = slide;
      this.startAnimation(this.messages[slide].displayTime);
    }
  },
  computed: {
    animatedAmount: function () {
      return this.tweenedNumber.toFixed(0);
    },
  },
  created() {},
  beforeDestroy() {
    if (this.animation) this.animation.kill();
  },
  mounted() {
    //make sure to fill available height. necessary due to limitations with vue-agile
    //todo add event listener to displayContainer, execute resize of this.height as its size changes
    this.height = this.$refs.displayContainer.clientHeight - 5 + "px";
    this.$watch("$refs.displayContainer.clientHeight", (new_value) => {
      this.height = new_value - 5 + "px";
    });

    //force update if state changes
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "setMessages") {
        if(this.animation) this.animation.kill();
        this.messages = this.$store.getters.messages(this.boardId);
        this.currentSlide = 0;
        this.start();
      }
    });
    if (this.autoStart) this.start();
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.display-container {
  height: 100%;
  pointer-events: none;
}

.message-container {
  width: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
}
.message-content {
  width: 100%;
  overflow-wrap: break-word;

  padding: 10%;
}
</style>
