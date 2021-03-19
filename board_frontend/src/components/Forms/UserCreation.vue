<template>
  <div>
    <form novalidate class="md-layout" @submit.prevent="validateUser" v-if="$store.getters.isSupervisor || user">
      <md-card class="md-layout-item md-size-50 md-small-size-100">
        <md-card-header>
          <div class="md-title">Benutzer</div>
        </md-card-header>

        <md-card-content>
          <div class="md-layout md-gutter">
            <div class="md-layout-item md-small-size-100">
              <md-field :class="getValidationClass('username')">
                <label>Nutzername</label>
                <md-input name="username" id="username" autocomplete="given-name" v-model="form.username" :disabled="sending" />
                <span class="md-error" v-if="!$v.form.username.required">The first name is required</span>
              </md-field>
            </div>
          </div>

          <md-field :class="getValidationClass('email')">
            <label>Email</label>
            <md-input type="email" name="email" id="email" autocomplete="email" v-model="form.email" :disabled="sending" />
            <span class="md-error" v-if="!$v.form.email.required">The email is required</span>
          </md-field>

          <md-field :class="getValidationClass('password')">
            <label>Passwort</label>
            <md-input type="password" name="password" id="password" autocomplete="password" v-model="form.password" :disabled="sending" />
            <span class="md-error" v-if="!$v.form.password.required">Password is required</span>
          </md-field>

          <md-field :class="getValidationClass('isSupervisor')">
            <label>Supervisor</label>
            <md-input type="checkbox" name="isSupervisor" id="isSupervisor" autocomplete="isSupervisor" v-model="form.isSupervisor" :disabled="sending" />
            <span class="md-error" v-if="!$v.form.isSupervisor.required">Role is required</span>
          </md-field>
        </md-card-content>

        <md-progress-bar md-mode="indeterminate" v-if="sending" />

        <div class="editUser" v-if="user">
          <md-card-actions>
            <md-button class="md-warning" @click="user = null" :disabled="sending">abbrechen</md-button>
          </md-card-actions>
          <md-card-actions>
            <md-button type="submit" class="md-primary" @click="action = 'edit'" :disabled="sending">Nutzerdaten aktualisieren</md-button>
          </md-card-actions>
        </div>

        <md-card-actions v-if="!user && $store.getters.isSupervisor">
          <md-button type="submit" class="md-primary" @click="action = 'create'" :disabled="sending">Nutzer erstellen</md-button>
        </md-card-actions>
      </md-card>

      <md-snackbar :md-active.sync="userSaved">The user {{ lastUser }} was saved with success!</md-snackbar>
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
import userapi from "@/http/userapi";

export default {
  name: 'UserCreation',
  mixins: [validationMixin],
  props: ['user'],
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
      username: {
        required,
      },
      email: {
        required,
        email
      },
      password: {
        required,
      },
      isSupervisor: {
        required,
      },
    }
  },
  computed: {
    form(){
      return  {
            username: this.$props.user ? this.$props.user.userName : null,
            email: this.$props.user ? this.$props.user.email : null,
            isSupervisor: this.$props.user ? this.isSupervisor() : false,
            password: this.$props.user ? this.$props.user.password : null,
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
      this.form.username = null
      this.form.isSupervisor = null
      this.form.password = null
      this.form.email = null
    },
    saveUser () {
      this.sending = true
      //create user
      const userToCreate = {
        'userName' : this.form.username,
        'password' : this.form.password,
        'email'    : this.form.email,
        'isSupervisor' : this.form.isSupervisor
      }
      if (this.action === 'create'){
        userapi.addUser(userToCreate).catch( error => {
          this.$toastr.error("Fehler beim Anlegen des Benutzers");
          this.$toastr.success("Fehler beim Anlegen des Benutzers");
          this.sending = false;
          this.clearForm();
        }).then( res => {
          if (res) {
            this.$emit('user-created',userToCreate)
            this.sending = false
            this.clearForm()
          }
        })
      }//update user
      else {
        userapi.updateUser(userToCreate, this.$props.user.id).catch( error => {
          this.$toastr.error("Fehler beim Aktualisieren des Benutzers");
          this.sending = false;
          this.clearForm();
        }).then( res => {
          if (res) {
            this.$emit('user-updated',userToCreate)
            this.sending = false
            this.clearForm()
          }
        })
      }
    },
    validateUser () {
      console.log(this.form)
      this.saveUser()

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
