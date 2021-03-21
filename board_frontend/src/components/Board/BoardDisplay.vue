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
      if (this.messages && this.messages.length > 1) {
        if (this.$refs.carousel && this.$refs.carousel.currentSlide) {
          this.startAnimation(
            this.messages[this.$refs.carousel.currentSlide].displayTime
          );
        } else {
          this.startAnimation(
            this.messages[0].displayTime
          )
        }
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
      if(this.$refs.carousel) try {
        this.$refs.carousel.goToNext();
      } catch(e){}
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
    handleNextSlide(slide) {
      this.currentSlide = slide;
      this.startAnimation(this.messages[slide].displayTime);
    },
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
    this.height = this.$refs.displayContainer.clientHeight - 5 + "px";
    this.$watch("$refs.displayContainer.clientHeight", (new_value) => {
      this.height = new_value - 5 + "px";
    });

    //force update if state changes
    let self = this;
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === "setMessages") {
        if (this.animation) this.animation.kill();
        let board = state.boards.find((board) => board.id == this.boardId);
        if (board.messages) {
          this.messages = board.messages;
        }

        self.currentSlide = 0;
        self.start();
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
