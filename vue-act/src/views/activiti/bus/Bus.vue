<template>
  <!--<el-table
    :data="tableData"
    style="width: 100%"
    :row-class-name="tableRowClassName"
  >
    <el-table-column prop="date" label="日期" width="180"> </el-table-column>
    <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
    <el-table-column prop="address" label="地址"> </el-table-column>
  </el-table>-->

  <el-form ref="form" style="width: 60%" :model="form" label-width="90px">
    <el-form-item label="请假类型">
      <el-select v-model="form.leaveType" placeholder="请选择请假类型">
        <el-option label="事假" value="事假"></el-option>
        <el-option label="病假" value="病假"></el-option>
        <el-option label="年假" value="年假"></el-option>
        <el-option label="丧假" value="丧假"></el-option>
        <el-option label="产假" value="产假"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="开始时间">
    <div class="block">
      <el-date-picker
              v-model="form.startTime"
              type="date"
              placeholder="选择日期">
      </el-date-picker>
    </div>
  </el-form-item>
    <el-form-item label="结束时间">
      <div class="block">
        <el-date-picker
                v-model="form.endTime"
                type="date"
                placeholder="选择日期">
        </el-date-picker>
      </div>
    </el-form-item>
    <el-form-item label="请假时长">
      <el-input style="width: 35%" v-model="form.leaveTime" />
    </el-form-item>

    <el-form-item label="请假原因">
      <el-input style="width: 35%"
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="form.reason">
      </el-input>
    </el-form-item>

    <!--<el-form-item label="职务代理人">
      <el-select v-model="form.userJobId" placeholder="请选择职务代理人">
        <el-option label="张三" value="张三"></el-option>
        <el-option label="李四" value="李四"></el-option>
        <el-option label="王五" value="王五"></el-option>
        <el-option label="赵六" value="赵六"></el-option>
      </el-select>
    </el-form-item>-->

    <el-form-item label="流程">
      <el-select v-model="form.proDefId" placeholder="请选择流程">
        <el-option
                v-for="item in processList"
                :key="item.ID_"
                :label="item.name"
                :value="item.proDefId"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
export default {
  created() {
    this.getProces();
  },

  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
    onSubmit() {
      this.$axios.post('/leave/startleave',this.form).then(res =>{
         console.log(res.data)
      }).catch()
    },
    getProces(){
      this.$axios.get('/model/start/models').then(res =>{
        this.processList = res.data.data;
      }).catch()
    }
  },
  data() {
    return {
      form: {
        leaveType: '',
        startTime: '2021/6/4',
        endTime: '2021/6/6',
        leaveTime: '',
        reason: '',
        proDefId:''
      },
      processList:[]
    };
  }
};
</script>