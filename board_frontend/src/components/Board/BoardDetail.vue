<template>
  <div class="page-container">
    <md-app class="page-container">
      <md-app-toolbar class="md-primary md-large">
        <div class="md-toolbar-row">
          <md-button class="md-icon-button" @click="menuVisible = !menuVisible">
            <md-icon>menu</md-icon>
          </md-button>

          <span class="md-title">Board {{ boardId }}</span>
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
          <md-button class="md-icon-button" :class="{'md-raised md-primary' : this.messageActive}"
                    @click="setActiveTab('message')">
            <md-icon>edit</md-icon>
          </md-button>
          <md-button class="md-icon-button" :class="{'md-raised md-primary' : this.usersActive }"
                    @click="setActiveTab('users')">
            <md-icon>person</md-icon>
          </md-button>
          <md-button class="md-icon-button" :class="{'md-raised md-primary' : this.messageManagementActive }"
                    @click="setActiveTab('messageManagement')">
            <md-icon>menu</md-icon>
          </md-button>
          <div class="board-form" v-if="this.messageActive">
            <md-steppers md-vertical>
              <md-step id="first" md-label="Nachricht verfassen">
                <vue-editor class="editor" v-model="content"></vue-editor>
              </md-step>

              <md-step id="second" md-label="Hintergrundfarbe wählen">
                <div class="colorpicker">
                  <span>Hintergrundfarbe</span>
                  <Chrome class="picker" v-model="colors"></Chrome>
                </div>
              </md-step>

              <md-step id="third" md-label="Ablaufdatum festlegen">
                <div class="third">
                <span>Anzeigen bis</span>
                <Datepicker v-model="date" class="datepicker"></Datepicker>
                </div>
              </md-step>
              <md-step id="fourth" md-label="Aktivität">
                <div class="fourth">
                  <input type="checkbox" v-model="active">
                  <span v-if="active">Aktiv</span>
                  <span v-if="!active">Inaktiv</span>
                </div>
              </md-step>
              <md-step id="fifth" md-label="Anzeigedauer">
                <div class="fifth">
                  <input type="number" v-model="displayTime">
                  <span> Sekunden</span>
                </div>
              </md-step>
              <md-step id="sixth" md-label="Absenden">
                <span v-if="!content">Vor dem Versenden müssen Sie erst eine Nachricht verfassen!</span>
                <md-button v-if="content && !sending" class="md-icon-button sendbutton blob"
                          :class="{'md-raised' : this.messageActive}"
                          @click="sendMessage">
                  <md-icon>send</md-icon>
                </md-button>
                <md-progress-spinner v-if="sending" md-mode="indeterminate"></md-progress-spinner>
              </md-step>
            </md-steppers>
          </div>
          <UserManagement v-if="this.usersActive"></UserManagement>
          <MessageManagement v-if="this.messageManagementActive"></MessageManagement>
        </div>
        <div class="board-preview md-layout-item" @click="showOverlay = !showOverlay">
          <BoardDisplay></BoardDisplay>
        </div>
        <div id="overlay" @click="showOverlay = !showOverlay"
              :class="{active : showOverlay, inactive : !showOverlay}">
          <div id="overlay-inner">
            <BoardDisplay v-if="showOverlay"></BoardDisplay>
          </div>
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
import UserManagement from "@/components/Board/UserManagement";
import MessageManagement from "@/components/Board/MessageManagement";
import boardservice from "@/services/boardService";
import BoardDisplay from "@/components/Board/BoardDisplay";


let SizeStyle = Quill.import('attributors/style/size');
Quill.register(SizeStyle, true);
let AlignStyle = Quill.import('attributors/style/align');
Quill.register(AlignStyle, true);

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker,
    UserManagement,
    MessageManagement,
    BoardDisplay,
  },
  props: {},
  data() {
    return {
      boardId: null,
      board: null,
      menuVisible: false,
      usersActive: false,
      showOverlay: false,
      sending: false,
      messageActive: true,
      messageManagementActive: false,
      content: "",
      colors: {},
      date: new Date(),
      active: true,
      displayTime: 10,
    };
  },
  methods: {
    setActiveTab(tab) {
      if (tab === 'message') {
        this.messageActive = true;
        this.usersActive = false;
        this.messageManagementActive = false;
      } else if (tab === 'users') {
        this.messageActive = false;
        this.usersActive = true;
        this.messageManagementActive = false;
      } else {
        this.messageManagementActive = true;
        this.messageActive = false;
        this.usersActive = false;
      }
    },
    sendMessage(){
      const message = {
        content: this.content,
        bgColor: this.colors.hex,
        //endDate: this.date,
        //yyyy-mm-dd hh:mm:ss[.fffffffff]]
        //while it seems to fit format 'yyyy-MM-dd'T'HH:mm:ss.SSSX', parsing fails (leniency? null))
        endDate: "2024-03-01 17:03:17",
        active: this.active,
        displayTime: this.displayTime
      }
      this.sending = true;
      this.$boardService.send(message)

      console.log(message);
    }
  },
  computed: {},
  created() {
    this.boardId = this.$route.params.id;
    //hier das passende Board anhand ID aus dem store holen und socket aufbauen für master
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
    width: 50%;
    margin-top: -16px;
    margin-right: -16px;
    margin-bottom: -16px;
    cursor: pointer;
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

.pot{bottom:15%;
  position:absolute;
  -webkit-animation:linear infinite alternate;
  -webkit-animation-name: run;
  -webkit-animation-duration: 5s;
}
@-webkit-keyframes run {
  0% { left: 0;}
  100%{ left : 100%;}
}

</style>
