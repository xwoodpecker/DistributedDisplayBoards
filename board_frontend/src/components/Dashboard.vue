<template>
    <div class="boards">
        Hier kommt die Übersicht aller Gruppen hin
        <!--<Board  v-for="board in boards" v-bind:key="board.id" msg="Test"></Board>-->
        <Board msg="Test"></Board>

        <button @click="logout">Ausloggen</button>
    </div>
</template>

<script>
    import Board from "./Board";
    import authenticationService from "../authenticationService";

    export default {
        name: 'Dasboard',
        props: {},
        components: {
            Board,
        },
        data() {
            return {
                user: this.$store.state.user,
            }
        },
        methods: {
            logout() {
                authenticationService.logout();
                this.$router.push({name: 'login'});
            }
        },
        computed: {},
        created() {

        },
        mounted() {

        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
