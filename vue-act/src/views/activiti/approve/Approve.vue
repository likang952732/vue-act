<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column fixed prop="executionId" label="执行id"> </el-table-column>
    <el-table-column fixed prop="processInstanceId" label="流程实例id"> </el-table-column>
    <el-table-column prop="businessKey" label="业务号"> </el-table-column>
    <el-table-column prop="activityId" label="当前节点"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="100">
      <template slot-scope="scope">
        <el-button @click="showPic(scope.row.deploymentId)" type="text" size="small"
          >查看流程图</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getBatch } from "@/api/activiti";
// let Base64 = require('js-base64').Base64;
export default {
  data() {
    return {
      tableData: [],
    };
  },
  created() {
    this.getProcessList();
  },
  methods: {
    showPic(id){
      // window.open('/process/show?did='+id+'&ext=.png', '')
      this.$axios.get('/process/show?did='+id+'&ext=.png').then(res =>{
        let url = 'data:image/png;base64,' + res.data
        console.log(url)
        window.open(url, '')

      }).catch()
    },

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
        path: "ApproveDetail",
        query: { approve_buskey: buskey },
      });
    },
    */
    getProcessList(){
      this.$axios.get('/leave/getProcessList?current=1&rowCount=10').then(res =>{
        this.tableData = res.data.rows;
      }).catch()
    }
  }


};
</script>