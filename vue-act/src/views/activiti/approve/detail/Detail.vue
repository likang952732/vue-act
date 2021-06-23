<template>
  <div class="detail">
    <el-input style="width: 35%"
              type="textarea"
              :rows="2"
              placeholder="请输入审批人"
              v-model="username">
    </el-input>
    <el-button type="primary" @click="getTask">查询</el-button>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column fixed prop="executionId" label="执行id"> </el-table-column>
      <el-table-column fixed prop="assignee" label="审批人"> </el-table-column>
      <el-table-column prop="name" label="当前操作"> </el-table-column>
      <el-table-column prop="taskDefKey" label="任务定义key"> </el-table-column>
      <el-table-column prop="createTime" label="创建时间"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="editTo(scope.row)" type="text" size="small"
          >同意</el-button
          >
          <el-button @click="reject(scope.row)" type="text" size="small"
          >拒绝</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="审批" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="审批意见">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click='agree'>确 定</el-button>
      </div>
    </el-dialog>

    <!--<el-tabs v-model="activeName" @tab-click="handleClick">
      <el-input style="width: 35%"
                type="textarea"
                :rows="2"
                placeholder="请输入审批人"
                v-model="username">
      </el-input>

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
    </el-tabs>-->
  </div>
</template>
<script>
import { processTask } from "@/api/activiti/approve";
export default {
  methods: {
    /* handleClick(tab, event) {
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
     },*/

    getTask(){
      this.$axios.get(`/task/list?userName=${this.username}`).then(res =>{
        this.tableData = res.data;
      }).catch()
    },
    editTo(row) {
      this.dialogFormVisible = true
      this.id = row.id
      this.form = {
        dealType:"0",
        rejectNodeId:row.startActId,
        startActId: row.startActId,
        dealUserId:this.username,
        procInstId:row.procInstId
      }
    },

    agree(){
      this.form = {
        ...this.form,
        dealReason:this.form.name,
      }
      this.$axios.post(`/runtime/tasks/do/`+this.id,this.form).then(res =>{
        this.dialogFormVisible = false
      }).catch()
    },

    reject(row){
      this.$axios.post(`/runtime/reject/`+row.id,{
        "dealReason":"没有完成好，不通过",
        "dealType":"1",
   /*   "rejectNodeId":row.startActId,
        "startActId": row.startActId,*/
        "dealUserId":this.username,
        "procInstId":row.procInstId
      }).then(res =>{
        alert('ok');
      }).catch()
    }
  },
  data() {
    return {
      activeName: "first",
      buskey: "",
      approveReason: "",
      username:'',
      tableData: [],
      userName:'',
      dialogFormVisible: false,
      form: {
        name:'',
      }
    };
  },
  created() {
    this.buskey = this.$route.query.approve_buskey;
    console.log("detail:" + this.buskey);
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