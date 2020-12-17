<template>
    <div id="app">
        <Login v-if="!this.$store.state.user"></Login>
        <div v-if="this.$store.state.user" class="boards">
            <!--<Board  v-for="board in boards" v-bind:key="board.id" msg="Test"></Board>-->
            <Board msg="Test"></Board>
        </div>
    </div>
</template>

<script>
    import Login from "./components/Login";
    import Board from "./components/Board";

    export default {
        name: 'App',
        components: {
            Board,
            Login
        },
        data() {
            return {
                user: null,
                boards: [],
            }
        },
        methods: {},
        created() {
            //Socket initialisieren + User setzen
        },
        updated() {
            console.log("Hier ist der User aus dem Store, gesetzt durch die Login Komponente", this.$store.state.user)
        }
    }
</script>

<style>
    @import './assets/css/fontawesome/css/font-awesome.css';

    @font-face {
        font-family: 'Nunito';
        font-style: normal;
        font-weight: 400;
        src: url("assets/fonts/Nunito/Nunito-Regular.ttf");
        unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215, U+FEFF, U+FFFD;
    }

    * {
        font-family: 'Nunito', sans-serif;
    }

    html,
    body {
        height: 100%;
        margin: 0;
        padding: 0;
        width: 100%;
    }
</style>
