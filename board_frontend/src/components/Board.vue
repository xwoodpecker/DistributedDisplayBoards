<template>
    <div class="hello">
        {{user.name}}
    </div>
</template>

<script>
    export default {
        name: 'Board',
        props: {},
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
