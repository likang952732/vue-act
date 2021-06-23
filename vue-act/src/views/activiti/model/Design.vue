
<template>
    <div style="position:relative;height: 100%;">
        <iframe
                id="iframe"
                :src="modelerUrl"
                frameborder="0"
                width="100%"
                height="720px"
                scrolling="auto"
        />
      <div v-if="modelerLoading" fix size="large" />
    </div>
</template>

<script>
    //import { getToken } from '@/utils/auth'
    export default {
        name: 'Design',
        components: {},

        data() {
            return {
                modelId: '',
                modelerLoading: true,
                //modelerUrl: '/static/modeler.html?modelId=' + this.$route.query.id + '&time=' + new Date().getTime()
                modelerUrl: '/static/modeler.html?modelId=' + localStorage.getItem('modelId') + '&time=' + new Date().getTime()
            }
        },
        computed: {
          /*  token() {
                return 'Bearer ' + getToken()
            }*/

        },
        created() {
        },
        mounted() {
            window.getMyVue = this
            this.newmodel();
        },
        methods: {
            newmodel(){
                this.$axios.get('/model/newModel').then(res =>{
                    this.modelId = res.data;
                    localStorage.setItem('modelId',res.data);
                }).catch()
            }
        }
    }
</script>
<style lang="scss" scoped>
    .iframe {
        width: 100%;
        height: calc(100vh - 154px);
    }
</style>