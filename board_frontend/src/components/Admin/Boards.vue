<template>
  <div class="page-container">
    <md-app class="page-container">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Boardverwaltung</span>
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
          <template v-if="groups.length">
            <md-list>
              <div class="singleBoard" v-for="board in groups">
                <SingleBoard :group="board"></SingleBoard>
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
import userapi from "@/http/userapi";


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
      groups: [],
      users: [],
      coordinator: null,
    };
  },
  methods: {
    deleteBoard(board) {
      boardsapi.deleteBoard(board.id).then(res => {
        if (res) {
          this.refreshBoards();
          this.$toastr.success(board.groupName + " erfolgreich gelöscht")
        }
      });
    },
    boardUpdated(board){
      this.$toastr.success(board.groupName + " erfolgreich aktualisiert")
      this.refreshBoards();
    },
    boardCreated(board){
      this.$toastr.success(board.groupName + " erfolgreich erstellt")
      this.refreshBoards();
    },
    refreshBoards() {
      boardsapi.getBoards().then(res => {
        if (res) {
          this.groups = res;
        }
      });
    },
    setCoordinator(group){
      console.log(coordinator);
      console.log(group);
      group.coordinator = this.coordinator.id,
      console.log(group);
      //boardsapi.updateBoard(group, group.id)
    }
  },
  computed: {
  },
  created() {
    if (this.$store.getters.hasRoleCoordinator){
      //user is coordinator
      this.groups = this.$store.getters.getCoordinatorBoards();
    } else {
      //user is supervisor
      boardsapi.getBoards().then(res => {
        if (res) {
          this.groups = res;
        }
      });
      userapi.getUsers().then(res => {
        if (res){
          this.users = res;
        }
      })
    }
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
}

.controls {
  display: flex;
  justify-content: flex-end;
}

.singleBoard {
  display: flex;
  flex-direction: column;
  margin: 25px 0 25px 0;
  background-color: #ebebeb;
  border-radius: 5px;
  padding: 5px;
}


</style>
