<template>
    <div class="boards">
        <!--<Board  v-for="board in boards" v-bind:key="board.id" msg="Test"></Board>-->
        <Board msg="Test"></Board>
    </div>
</template>

<script>
    import Board from "./Board";

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
        methods: {},
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
