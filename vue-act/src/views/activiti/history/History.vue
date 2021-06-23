<template>

  <div>
    <el-input style="width: 35%"
              type="textarea"
              :rows="2"
              placeholder="请输入姓名"
              v-model="username">
    </el-input>
    <el-button type="primary" @click="getHistory(username)">查询</el-button>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column fixed prop="name" label="名称"> </el-table-column>
      <el-table-column prop="assignee" label="审批人"> </el-table-column>
      <el-table-column fixed prop="approveReason" label="审批意见"> </el-table-column>
      <el-table-column prop="startTime" label="开始时间"> </el-table-column>
      <el-table-column prop="endTime" label="结束时间"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getBatch } from "@/api/activiti";

export default {
  data() {
    return {
      tableData: [],
      username:''
    };
  },
  created() {

  },
  methods: {
    getHistory(username){
      this.$axios.get(`/history/tasks/myc?userId=${this.username}`).then(res =>{
        this.tableData = res.data;

      }).catch()
    }

   /* getBatchList: function () {
      //自定义的方法
      let params = {
        fromPlatform: "", // 平台涞源
        nickName: "", // 网名
        pageSize: "", // 每页几条数据
        pageNum: "", // 当前页数
      };
      // 根据昵称和平台搜索评论内容
      getBatch(params).then((res) => {
        console.log(res.data);
        this.tableData = res.data.list;
      });
      // console.log(this.searchImgListDate)
    },
    show(buskey) {
      console.log(buskey);
      this.$router.push({
        path: "QueryDetail",
        query: { approve_buskey: buskey },
      });
    },*/
  },
};
</script>