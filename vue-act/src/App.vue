<template>
  <div id="app">
    <!--<router-view />-->
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
</template>

<script>
import "./assets/css/base.css";
export default {
  name: "App",
};
</script>

<style >
#app {
  width: 100%;
  height: 100%;
}
</style>
