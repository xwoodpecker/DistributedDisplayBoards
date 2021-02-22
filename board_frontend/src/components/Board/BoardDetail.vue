<template>
  <div class="page-container">
    <md-app>
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

      <md-app-content class="board-content">
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
        <md-button class="md-icon-button">
          <md-icon>menu</md-icon>
        </md-button>
        <div class="board-">
          <div class="board-form" v-if="this.messageActive">
            <vue-editor class="editor" v-model="content"></vue-editor>
            <div class="controls">
              <div class="colorpicker">
                <span>Hintergrundfarbe</span>
                <Chrome class="picker" v-model="colors"></Chrome>
              </div>
              <div class="settings">
                <span>Anzeigen bis</span>
                <Datepicker v-model="date" class="datepicker"></Datepicker>
              </div>
              <div>
                <md-button  v-if="content" class="md-icon-button sendbutton blob" :class="{'md-raised' : this.messageActive}"
                           @click="setActiveTab('message')">
                  <md-icon>add</md-icon>
                </md-button>
              </div>
            </div>


            <!--            <div v-html="content"></div>
            &lt;!&ndash;          <BoardDetailForm></BoardDetailForm>&ndash;&gt;-->
          </div>
          <UserManagement v-if="this.usersActive"></UserManagement>
          <div class="board-preview" @click="showOverlay = !showOverlay">

          </div>
          <div id="overlay" @click="showOverlay = !showOverlay"
               :class="{active : showOverlay, inactive : !showOverlay}">
            <h1>iltis</h1>
          </div>
        </div>
      </md-app-content>
    </md-app>
  </div>
</template>

<script>
//import BoardDetailForm from "@/components/Board/BoardDetailForm";
import Sidebar from "@/components/Layout/Sidebar";
import {VueEditor} from "vue2-editor";
import Chrome from "vue-color/src/components/Chrome"
import Datepicker from 'vuejs-datepicker';
import UserManagement from "@/components/Board/UserManagement";

export default {
  name: "BoardDetail",
  components: {
    //BoardDetailForm,
    Sidebar,
    VueEditor,
    Chrome,
    Datepicker,
    UserManagement
  },
  props: {},
  data() {
    return {
      boardId: null,
      board: null,
      menuVisible: false,
      usersActive: false,
      showOverlay: false,
      messageActive: true,
      content: "",
      colors: {
      },
      date: new Date()
    };
  },
  methods: {
    setActiveTab(tab) {
      if (tab === 'message') {
        this.messageActive = true;
        this.usersActive = false;
      } else {
        this.messageActive = false;
        this.usersActive = true;
      }
    }
  },
  computed: {},
  created() {
    this.boardId = this.$route.params.id;

    //hier das passende Board anhand ID aus dem store holen und socket aufbauen für master
    console.log("Board Id is" + this.boardId)
  },
  mounted() {
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.board {
  &-content {
    height: 100%;
    border-right: none;
  }

  &-overview {
    margin-top: 30px;
    height: 100vh;
  }

  &-preview {
    cursor: pointer;
    width: 50%;
    height: 100%;
    position: absolute;
    top: 0;
    right: 0;
    background-color: #ebebeb;
  }

  &-form {
    width: 50%;
    margin-top: 15px;
    position: relative;

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
      overflow: scroll;
      margin-right: 15px;
    }

  }
}

.sendbutton {
  background-color: #B4D7A8 !important;
  margin: 15px 0 15px 15px;
  position: absolute;
  right: 10px;
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
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  z-index: 888;
  cursor: pointer;
}

.active {
  display: block;
}

.inactive {
  display: none;
}

</style>
