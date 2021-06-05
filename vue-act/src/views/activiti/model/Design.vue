
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
                modelerUrl: '/static/modeler.html?modelId=' + '174e18951fcb45d8892b31b6c89c8c15' + '&time=' + new Date().getTime()
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
                    //alert('modelId: ' + res.data)
                    this.modelId = res.data;
                    console.log('this.modelId: ' + this.modelId)
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