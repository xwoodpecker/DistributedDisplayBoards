<template>
  <div>
    <h2>Nachrichtenarchiv</h2>
    <md-list class="md-double-line userlist">
      <md-list-item v-for="message in messages" :key="message.id">
        <md-avatar :style="'background-color: ' + message.bgColor"> </md-avatar>

        <div class="md-list-item-text">
          <span>Aktiv bis: {{ new Date(message.endDate).toDateString() }}</span>
          <p>
            {{ message.content }}
          </p>
        </div>
        <md-button
          class="md-icon-button md-list-action"
          @click="onClickEdit(message.id)"
          v-if="canEdit(message.id)"
        >
          <md-icon class="md-primary">edit</md-icon>
        </md-button>
        <md-button
          class="md-icon-button md-list-action"
          @click="onClickDelete(message.id)"
          v-if="message.active"
        >
          <md-icon class="md-primary">delete</md-icon>
        </md-button>
      </md-list-item>
    </md-list>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import { VueEditor } from "vue2-editor";
import Chrome from "vue-color/src/components/Chrome";
import Datepicker from "vuejs-datepicker";
import messageapi from "@/http/messageapi";

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker,
  },
  props: {
    boardId: {
      type: Number,
    },
  },
  data() {
    return {
      messages: [],
    };
  },
  methods: {
    canEdit(messageId) {
      let boards = this.$store.getters.getCoordinatorBoards;
      let isCoordinator = boards.find(board => board.id == this.boardId);
      return (
        this.$store.getters.isSupervisor || isCoordinator ||
        this.messages.find((message) => message.id === messageId).user ==
          this.$store.getters.getUser
      );
    },
    onClickEdit(messageId) {
      console.log(messageId);
      this.$emit("editClicked", messageId);
    },
    onClickDelete(messageId) {
      let message = this.messages.find((message) => message.id == messageId);
      this.$boardService.updateMessage(
        message.id,
        message.content,
        message.board,
        message.displayTime,
        message.endDate,
        message.bgColor,
        false
      );
      messageapi.getMessagesOfBoard(this.boardId).then((messages) => {
        this.messages = messages;
        if(!messages.find((message) => message.id == messageId && message.active)){
          this.$toastr.success("Nachricht erfolgreich deaktiviert!");
        } else {
          this.$toastr.error("Beim Deaktivieren der Nachricht ist ein Fehler aufgetreten.")
        }
      });
    },
  },
  computed: {},
  created() {},
  mounted() {
    messageapi.getMessagesOfBoard(this.boardId).then((messages) => {
      this.messages = messages;
    });
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
button {
  margin-top: 25px;
}
.userlist {
  height: 100vh;
  width: 50%;
  margin: 25px 25px 15px 0;
  display: flex;
  flex-direction: column;
}
</style>
