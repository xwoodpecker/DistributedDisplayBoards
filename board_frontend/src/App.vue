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
