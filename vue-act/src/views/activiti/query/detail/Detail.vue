<template>
  <div class="detail">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="业务信息" name="first">
        <div class="block">
          <el-carousel trigger="click" height="150px">
            <el-carousel-item v-for="item in 4" :key="item">
              <h3 class="small">{{ item }}</h3>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="reason">
          <div class="title">审批意见:</div>
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="approveReason"
          >
          </el-input>
        </div>
        <div class="button">
          <el-button @click="approve(0)">通过</el-button>
          <el-button @click="approve(1)">拒绝</el-button>
          <el-button @click="goBack">返回</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="审批历史" name="second"> </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { processTask } from "@/api/activiti/approve";
export default {
  data() {
    return {
      activeName: "first",
      buskey: "",
      approveReason: "",
    };
  },
  created() {
    this.buskey = this.$route.query.approve_buskey;
    console.log("detail:" + this.buskey);
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    approve(approveState) {
      console.log(approveState);
      //自定义的方法
      let params = {
        busKey: this.buskey,
        approveState: approveState,
        approveReason: this.approveReason,
      };
      processTask(params).then((res) => {
        console.log(res.data);
      });
    },

    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.detail {
  width: 80%;
  height: 100%;
  margin: 2% auto;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.reason {
  width: 50%;
  margin: 4% auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.reason .title {
  width: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.button {
  width: 30%;
  margin: 4% auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>