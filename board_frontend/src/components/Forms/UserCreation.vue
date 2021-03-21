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
                <md-input name="username" id="username" autocomplete="given-name" v-model="form.username"
                          :disabled="sending"/>
                <span class="md-error" v-if="!$v.form.username.required">Nutzername ist ein Pflichtfeld</span>
              </md-field>
            </div>
          </div>

          <md-field :class="getValidationClass('email')">
            <label>Email</label>
            <md-input type="email" name="email" id="email" autocomplete="email" v-model="form.email"
                      :disabled="sending"/>
            <span class="md-error" v-if="!$v.form.email.required">Email muss eine valide Email-Adresse sein.</span>
          </md-field>

          <md-field :class="getValidationClass('isSupervisor')" v-if="$store.getters.isSupervisor">
            <label>Supervisor</label>
            <md-input type="checkbox" name="isSupervisor" id="isSupervisor" autocomplete="isSupervisor"
                      v-model="form.isSupervisor" :disabled="sending"/>
          </md-field>

          <md-field v-if="user">
            <label>
              Passwort ändern
            </label>
            <md-input type="checkbox" name="changePw" id="changePw" v-model="changePw"/>
          </md-field>

          <md-field :class="getValidationClass('password')" v-show="(user && changePw) || !user">
            <label>Passwort</label>
            <md-input type="password" name="password" id="password" autocomplete="password" v-model="form.password"
                      :disabled="sending"/>
          </md-field>
        </md-card-content>

        <md-progress-bar md-mode="indeterminate" v-if="sending"/>

        <div class="editUser" v-if="user">
          <md-card-actions>
            <md-button class="md-warning" @click="user = null" :disabled="sending">abbrechen</md-button>
          </md-card-actions>
          <md-card-actions>
            <md-button type="submit" class="md-primary" @click="setAction('edit')" :disabled="sending">Nutzerdaten
              aktualisieren
            </md-button>
          </md-card-actions>
        </div>

        <md-card-actions v-if="!user && $store.getters.isSupervisor">
          <md-button type="submit" class="md-primary" @click="setAction('create')" :disabled="sending || form.password.length < 8">Nutzer erstellen
          </md-button>
        </md-card-actions>
      </md-card>

      <md-snackbar :md-active.sync="userSaved">The user {{ lastUser }} was saved with success!</md-snackbar>
    </form>
  </div>
</template>

<script>
import {validationMixin} from 'vuelidate'
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
    action: 'create',
    changePw: false,
    form: {
      'username': "",
      'email': "",
      'isSupervisor': "",
      'password': "",
    }
  }),
  watch: {
    user: {
      handler(user) {
        this.form = {
          'username': user ? user.userName : null,
          'email': user ? user.email : null,
          'isSupervisor': user ? this.isSupervisor() : false,
          'password': "",
        }
      }
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
    }
  },
  computed: {
  },
  methods: {
    isSupervisor() {
      let isSupervisor = false;
      this.$props.user.roles.forEach(role => {
        if (role.name === "SUPERVISOR") isSupervisor = true;
      })
      return isSupervisor;
    },
    setAction(action) {
      this.action = action
    },
    getValidationClass(fieldName) {
      const field = this.$v.form[fieldName]

      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },
    clearForm() {
      this.$v.$reset()
      this.form.username = null
      this.form.isSupervisor = null
      this.form.password = ""
      this.form.email = null
    },
    saveUser() {
      this.sending = true
      //create user
      const userToCreate = {
        'userName': this.form.username,
        'password': this.form.password,
        'email': this.form.email,
        'isSupervisor': this.form.isSupervisor
      }
      let userToUpdate = {
        'userName': this.form.username,
        'email': this.form.email,
        'isSupervisor': this.form.isSupervisor
      }
      if (this.changePw) {
        userToUpdate.password = this.form.password;
      }
      if (this.action === 'create') {
        userapi.addUser(userToCreate).catch(error => {
          this.$toastr.error("Fehler beim Anlegen des Benutzers");
          this.$toastr.success("Fehler beim Anlegen des Benutzers");
          this.sending = false;
          this.clearForm();
        }).then(res => {
          if (res) {
            this.$emit('user-created', userToCreate)
            this.sending = false
            this.clearForm()
          }
        })
      }//update user
      else {
        userapi.updateUser(userToUpdate, this.$props.user.id).catch(error => {
          this.$toastr.error("Fehler beim Aktualisieren des Benutzers");
          this.sending = false;
          this.clearForm();
        }).then(res => {
          if (res) {
            this.$emit('user-updated', userToCreate)
            this.sending = false
          }
        })
      }
    },
    validateUser() {
      console.log(this.$v.form)
      //TODO Formvalidierung checken
      if (this.changePw) {
      }
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.saveUser()
      }
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
