<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column fixed prop="batch_id" label="主键"> </el-table-column>
    <el-table-column prop="buskey" label="批次号"> </el-table-column>
    <el-table-column prop="createtime" label="时间"> </el-table-column>
    <el-table-column fixed="right" label="操作" width="100">
      <template slot-scope="scope">
        <el-button @click="show(scope.row.buskey)" type="text" size="small"
          >审批</el-button
        >
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
  created: function () {
    this.getBatchList();
  },
  methods: {
    getBatchList: function () {
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
  },
};
</script>