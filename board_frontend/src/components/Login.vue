<template>
    <div>
        <div class="login_container">
            <div class="login">
                <div class="description">
                    <h1>Login</h1>
                    <p>Bitte loggen Sie sich mit ihrem Benutzername und Passwort ein, um auf die Boards zugreifen zu
                        können.</p>
                </div>
                <div class="form">
                    <form @submit.prevent="doLogin">
                        <label for="username">Benutzername</label>
                        <input type="text" id="username" v-model="username" placeholder="MaxMustermann">

                        <label for="password">Passwort</label>
                        <i class="fa" :class="[passwordIcon]" @click="hidePassword = !hidePassword"></i>
                        <input :type="passwordType" id="password" v-model="password" placeholder="**********">
                        <ul v-if="errors.length">
                            <li class="error" v-bind:key="error" v-for="error in errors">{{ error }}</li>
                        </ul>

                        <button type="submit">Anmelden</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    //import socket from "sockjs";
    import userapi from "@/http/userapi";
    import authenticationService from "@/authenticationService";
    export default {
        name: 'Login',
        props: {},
        data() {
            return {
                errors: [],
                username: "",
                password: "",
                hidePassword: true
            }
        },
        computed: {
            passwordType() {
                return this.hidePassword ? 'password' : 'text'
            },
            passwordIcon() {
                return this.hidePassword ? 'fa-eye' : 'fa-eye-slash'
            }
        },
        methods: {
            async doLogin() {
                if (this.checkForm()) {
                    const credentials = {
                          'password': this.password,
                          'username': this.username,
                    };
                    await authenticationService.login(credentials).then( (res) => {
                      if (res) {
                        this.$store.commit('login', res);
                        this.$store.commit('setAuthHeader', credentials);
                        this.$router.push({name: 'dashboard'});
                      }
                    });
                    //this.$router.push({name: 'dashboard'});
                }
            },
            checkForm() {
                this.errors = [];
                if (!this.username) {
                    this.errors.push('Bitte geben Sie einen Benutzername ein.');
                }
                if (!this.password) this.errors.push('Bitte geben Sie ein Passwort ein.');
                return !this.errors.length;
            },
        },
        created() {
            //socket.createServer();
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

    .login_container {
        width: 100%;
        height: 100vh;

        .login {
            align-items: center;
            background-color: #e2e2e5;
            display: flex;
            justify-content: center;
            width: 100%;
            height: 100%;

            .description {
                background-color: #ffffff;
                width: 280px;
                padding: 35px;

                p {
                    font-size: 0.8em;
                    color: #95a5a6;
                    margin-top: 10px;
                }
            }

            .form {
                background-color: #34495e;
                border-radius: 5px;
                box-shadow: 0px 0px 30px 0px #666;
                color: #ecf0f1;
                width: 260px;
                padding: 35px;

                input, label {
                    outline: none;
                    width: 100%;
                }

                input {
                    background-color: transparent;
                    border: none;
                    color: #ecf0f1;
                    font-size: 1em;
                    margin-bottom: 20px;
                }

                label {
                    color: #95a5a6;
                    font-size: 0.8em;
                }

                button {
                    background-color: #ffffff;
                    cursor: pointer;
                    border: none;
                    padding: 10px;
                    transition: background-color 0.2s ease-in-out;
                    width: 100%;

                    &:hover {
                        background-color: #eeeeee;
                    }
                }

                i {
                    margin-left: 5px;
                }

                ul {
                    .error {
                        font-size: 12px;
                        color: indianred;
                    }
                }
            }

            &::placeholder {
                color: #ecf0f1;
                opacity: 1;
            }
        }
    }
</style>
