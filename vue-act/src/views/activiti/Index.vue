<template>
  <div style="width: 100%; height: 100%">
    <el-container class="index-container" style="width: 100%; height: 100%">
      <el-header>Header</el-header>
      <el-container>
        <el-aside style="width: auto; height: 100%">
          <el-menu
            default-active="1-4-1"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            :collapse="isCollapse"
          >
            <el-menu-item index="1" @click="go('Bus')">
              <i class="el-icon-s-home"></i>
              <span slot="title">请假开始</span>
            </el-menu-item>
            <el-menu-item index="2" @click="go('Approve')">
              <i class="el-icon-warning"></i>
              <span slot="title">我发起的流程</span>
            </el-menu-item>
            <el-menu-item index="3" @click="go('Query')">
              <i class="el-icon-menu"></i>
              <span slot="title">模型列表</span>
            </el-menu-item>
            <el-menu-item index="4" @click="go('Design')">
              <i class="el-icon-menu"></i>
              <span slot="title">模型设计</span>
            </el-menu-item>
            <el-menu-item index="5" @click="go('ApproveDetail')">
              <i class="el-icon-menu"></i>
              <span slot="title">审批任务</span>
            </el-menu-item>
            <el-menu-item index="6" @click="go('History')">
              <i class="el-icon-menu"></i>
              <span slot="title">审批历史</span>
            </el-menu-item>

          </el-menu>
        </el-aside>
        <el-container class="container-main">
          <el-main>
            <div v-show="!isCollapse" style="margin-bottom: 10px">
              <i
                @click="changeCollapse()"
                style="font-size: 15px"
                class="el-icon-caret-left"
              ></i>
            </div>
            <div v-show="isCollapse" style="margin-bottom: 10px">
              <i
                @click="changeCollapse()"
                style="font-size: 15px"
                class="el-icon-caret-right"
              ></i>
            </div>
            <!-- <div>
              <i class="el-icon-menu"></i>
              <span slot="title"></span>
            </div> -->
            <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">
              <el-radio-button :label="false">展开</el-radio-button>
              <el-radio-button :label="true">收起</el-radio-button>
            </el-radio-group> -->

            <div style="width: 100%; height: 100%">
              <router-view />
            </div>
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: true,
    };
  },
  methods: {
    changeCollapse() {
      this.isCollapse = !this.isCollapse;
      console.log(this.isCollapse);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
      console.log(this.isCollapse);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    //访问页面
    go(val) {
      this.$router.push(val);
    },
  },
};
</script>
<style scoped>
.index-container {
  width: 100%;
  height: 100%;
}
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  border-right: 1px solid #dcdfe6;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.container-main {
}
</style>