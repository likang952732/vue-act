<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column fixed prop="ID_" label="模型编号"> </el-table-column>
    <el-table-column fixed prop="name" label="模型名称"> </el-table-column>
    <el-table-column prop="revision" label="版本"> </el-table-column>
    <el-table-column prop="CREATE_TIME_" label="创建时间"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="100">
      <template slot-scope="scope">
        <el-button @click="show(scope.row.buskey)" type="text" size="small"
          >编辑</el-button
        >
        <el-button @click="myDelete(scope.row.ID_)" type="text" size="small"
        >删除</el-button
        >
        <el-button @click="develop(scope.row.ID_)" type="text" size="small"
        >发布</el-button
        >
       <!-- <el-button @click="showPic(scope.row.DEPLOYMENT_ID_)" type="text" size="small"
        >查看流程</el-button
        >-->
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import { getBatch } from "@/api/activiti";

export default {
  data() {
    return {
      tableData: [],
    };
  },
  created() {
    this.getModels();
  },
  methods: {
    getModels(){
      this.$axios.get('/model/models').then(res =>{
        this.tableData = res.data.data;
      }).catch()
    },

    myDelete(id){
      this.$axios.get('/model/delete/'+id).then(res =>{
        this.getModels();
      }).catch()
    },

    develop(id){
      this.$axios.post('/model/'+id+'/deployment').then(res =>{
        alert('ok')
        this.getModels();
      }).catch()
    },
    showPic(id){
      this.$axios.get('/process/show?did='+id+'&ext=.png').then(res =>{
        alert('ok')
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