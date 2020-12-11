<template>
    <div class="login">
        <div class="hello">
            {{message}}
        </div>
        <button @click="login">Einloggen</button>
    </div>
</template>

<script>
    export default {
        name: 'Login',
        props: {},
        data() {
            return {
                message: "Ich bin die Login Komponente",
            }
        },
        methods: {
            login() {
                const user = {
                    name: "lukas",
                };
                this.$store.commit('login', user);
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
<style scoped lang="scss">

</style>
