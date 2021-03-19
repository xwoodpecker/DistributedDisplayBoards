<template>
  <div class="page-container">
    <md-app class="page-container">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Boardverwatung</span>
        </div>
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <Sidebar></Sidebar>
      </md-app-drawer>

      <md-app-content class="board-content md-layout">
        <div class="board-details md-layout-item">
          <md-button @click='$router.push({ name: "dashboard" })' class="md-icon-button">
            <md-icon>home</md-icon>
          </md-button>
          <md-button class="md-icon-button md-raised md-primary">
            <md-icon>person</md-icon>
          </md-button>
          <template v-if="boards.length">
            <md-list>
              <div class="singleUser" v-for="board in boards">
                <SingleBoard :board="board"></SingleBoard>
                <div class="controls">
                  <md-button @click="currentBoard = board" class="md-icon-button md-list-action">
                    <md-icon class="md-primary">edit</md-icon>
                  </md-button>
                  <md-button @click="deleteBoard(board)" class="md-icon-button md-list-action">
                    <md-icon class="md-primary">delete</md-icon>
                  </md-button>
                </div>
              </div>
            </md-list>
          </template>
        </div>
        <div class="board-preview md-layout-item">
          <BoardCreation v-on:board-updated="boardUpdated" v-on:board-created="boardCreated"  :board="currentBoard"></BoardCreation>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import {VueEditor, Quill} from "vue2-editor";
import Chrome from "vue-color/src/components/Chrome"
import Datepicker from 'vuejs-datepicker';
import MessageManagement from "@/components/Board/MessageManagement";
import BoardDisplay from "@/components/Board/BoardDisplay";
import SingleBoard from "@/components/Forms/SingleBoard";
import BoardCreation from "@/components/Forms/BoardCreation";
import boardsapi from "@/http/boardsapi";


let SizeStyle = Quill.import('attributors/style/size');
Quill.register(SizeStyle, true);
let AlignStyle = Quill.import('attributors/style/align');
Quill.register(AlignStyle, true);

export default {
  name: "Users",
  components: {
    SingleBoard,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker,
    MessageManagement,
    BoardDisplay,
    BoardCreation
  },
  props: {},
  data() {
    return {
      menuVisible: false,
      currentBoard: null,
      boards: [],
    };
  },
  methods: {
    deleteBoard(board) {
      boardsapi.deleteBoard(board.id).then(res => {
        if (res) {
          this.refreshBoards();
          this.$toastr.success(board.boardName + " erfolgreich gelöscht")
        }
      });
    },
    boardUpdated(board){
      this.$toastr.success(board.boardName + " erfolgreich aktualisiert")
      this.refreshBoards();
    },
    boardCreated(board){
      this.$toastr.success(board.boardName + " erfolgreich erstellt")
      this.refreshBoards();
    },
    refreshBoards() {
      boardsapi.getBoards().then(res => {
        if (res) {
          this.boards = res;
        }
      });
    }
  },
  computed: {
  },
  created() {
    boardsapi.getBoards().then(res => {
      if (res) {
        this.boards = res;
      }
    });
  },
  mounted() {
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.page-container {
  height: 100vh;
}

.board {
  &-content {
    height: 100%;
  }

  &-details {
    width: 50%;
    padding-right: 16px;
  }

  &-preview {
    margin: 60px 0px 0px 0px;
  }

  &-form {
    margin-top: 15px;

    .controls {
      margin-top: 15px;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;

      .colorpicker {
        z-index: 187;
        max-width: 50vw;

        .picker {
        }
      }

      .settings {
        margin-left: 15px;

        .datepicker {
        }
      }
    }

    .editor {
      max-height: 300px;
      overflow-y: scroll;
      margin-right: 15px;
    }
  }

}

.sendbutton {
  margin-top: 15px;
}

.blob {
  box-shadow: 0 0 0 0 rgba(0, 0, 0, 1);
  transform: scale(1);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(0.95);
    box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.7);
  }

  70% {
    transform: scale(1);
    box-shadow: 0 0 0 10px rgba(0, 0, 0, 0);
  }

  100% {
    transform: scale(0.95);
    box-shadow: 0 0 0 0 rgba(0, 0, 0, 0);
  }
}

#overlay {
  position: absolute;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  z-index: 888;
  cursor: pointer;

  &-inner {
    width: 100vw;
    height: 100%;
  }
}

.active {
  display: block;
}

.inactive {
  display: none;
}

.third {
  min-height: 300px;
}

.pot {
  bottom: 15%;
  position: absolute;
  -webkit-animation: linear infinite alternate;
  -webkit-animation-name: run;
  -webkit-animation-duration: 5s;
}

@-webkit-keyframes run {
  0% {
    left: 0;
  }
  100% {
    left: 100%;
  }
}

.singleUser {
  display: flex;
  justify-content: space-between;
}


</style>
