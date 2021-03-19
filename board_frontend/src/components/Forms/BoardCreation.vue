<template>
  <div>
    <form novalidate class="md-layout" @submit.prevent="validateUser">
      <md-card class="md-layout-item md-size-50 md-small-size-100">
        <md-card-header>
          <div class="md-title">Board</div>
        </md-card-header>

        <md-card-content>
          <div class="md-layout md-gutter">
            <div class="md-layout-item md-small-size-100">
              <md-field :class="getValidationClass('boardName')">
                <label>Boardname</label>
                <md-input name="boardname" id="boardname" autocomplete="given-name" v-model="form.boardName" :disabled="sending" />
                <span class="md-error" v-if="!$v.form.boardName.required">The boardname is required</span>
              </md-field>
            </div>
          </div>

          <md-field :class="getValidationClass('location')">
            <label>Location</label>
            <md-input type="location" name="location" id="location" autocomplete="location" v-model="form.location" :disabled="sending" />
            <span class="md-error" v-if="!$v.form.location.required">The email is required</span>
          </md-field>
        </md-card-content>

        <md-progress-bar md-mode="indeterminate" v-if="sending" />

        <div class="editUser" v-if="board">
          <md-card-actions>
            <md-button class="md-warning" @click="board = null" :disabled="sending">abbrechen</md-button>
          </md-card-actions>
          <md-card-actions>
            <md-button type="submit" class="md-primary" @click="action = 'edit'" :disabled="sending">Board aktualisieren</md-button>
          </md-card-actions>
        </div>

        <md-card-actions v-if="!board">
          <md-button type="submit" class="md-primary" @click="action = 'create'" :disabled="sending">Board erstellen</md-button>
        </md-card-actions>
      </md-card>
    </form>
  </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import {
  required,
  email,
  minLength,
  maxLength
} from 'vuelidate/lib/validators'
import boardsapi from "@/http/boardsapi";

export default {
  name: 'BoardCreation',
  mixins: [validationMixin],
  props: ['board'],
  data: () => ({
    userSaved: false,
    sending: false,
    lastUser: null,
    action: 'create'
  }),
  watch: {
    user : (user) => {
      console.log(user)
      //this.action = 'edit'
    }
  },
  validations: {
    form: {
      boardName: {
        required,
      },
      location: {
        required,
      },
    }
  },
  computed: {
    form(){
      return  {
            boardName: this.$props.board ? this.$props.board.boardName : null,
            location: this.$props.board ? this.$props.board.location : null,
      }
    }
  },
  methods: {
    isSupervisor()
    {
      let isSupervisor = false;
      this.$props.user.roles.forEach( role => {
        if (role.name === "SUPERVISOR") isSupervisor = true;
      })
      return isSupervisor;
    },
    getValidationClass (fieldName) {
      const field = this.$v.form[fieldName]

      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },
    clearForm () {
      this.$v.$reset()
      this.form.location = null
      this.form.boardname = null
    },
    saveBoard () {
      this.sending = true
      //create user
      const boardToCreate = {
        'boardName' : this.form.boardName,
        'location' : this.form.location
      }
      if (this.action === 'create'){
        boardsapi.addBoard(boardToCreate).then( res => {
          if (res) {
            this.$emit('board-created',boardToCreate)
            this.sending = false
            this.clearForm()
          }
        })
      }//update board
      else {
        boardsapi.updateBoard(boardToCreate, this.$props.board.id).then( res => {
          if (res) {
            this.$emit('board-updated',boardToCreate)
            this.sending = false
            this.clearForm()
          }
        })
      }
    },
    validateUser () {
      this.saveBoard()
      //TODO Formvalidierung checken
      /*this.$v.$touch()
      if (!this.$v.$invalid) {
        this.saveUser()
      }*/
    }
  }
}
</script>

<style lang="scss" scoped>
.md-progress-bar {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
}
</style>
