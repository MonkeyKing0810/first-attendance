<template>
  <div>
    <div class="a">
      <span>考勤管理</span>
      <a
        id="linkUrl"
        href='http://localhost:8091/exportAttendance?fileName="考勤表"'
        title="下载"
        v-if="flag"
      >
        <el-button type="warning" icon="el-icon-download"
          >导出全部数据</el-button
        >
      </a>
    </div>

    <!-- 条件查询表格 -->
    <div class="b">
      <el-form style="width: 590px" :model="ruleForm">
        <el-form-item prop="pass" :inline-message="true">
          姓名：<el-select
            v-model="ruleForm.userId"
            placeholder="请选择"
            style="width: 100px"
          >
            <el-option
              v-for="item in Users"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>

          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间：
          <el-date-picker
            v-model="ruleForm.leaveTime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item style="width: 180px; height: 30px">
          <el-button
            type="primary"
            icon="el-icon-search"
            class="querybutton"
            @click="queryAttendanceInfo()"
            >查询</el-button
          >
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据展示表格 -->
    <div class="c">
      <el-table
        :data="attendance"
        :header-cell-style="{ color: 'red' }"
        @cell-mouse-enter="enter"
        @cell-mouse-leave="showClickIcon = false"
      >
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>

        <el-table-column prop="userName" label="姓名" width="100">
        </el-table-column>

        <el-table-column prop="requiredDays" label="应出勤天数" width="100">
        </el-table-column>

        <el-table-column
          label="实际出勤天数（不含加班）"
          width="200"
          align="center"
        >
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              请假日期在:
              <li v-for="(item, index) in leaveTimes" :key="index">
                {{ item }}
              </li>
              之间
              <div slot="reference">
                <el-tag size="medium">{{ scope.row.actualDays }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>

        <el-table-column label="加班天数" width="300" align="center">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              加班日期在:
              <li v-for="item in overtimeDays" :key="item">
                {{ item }}
              </li>
              之间
              <div slot="reference">
                <el-tag size="medium">{{ scope.row.overtimeDays }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  //script
  data() {
    return {
      //data
      Users: [],
      leaveTimes: [],
      overtimeDays: [],
      attendance: [],
      ruleForm: {
        userId: null,
        leaveTime: [],
      },
      currentPage: 1,
      total: "",
      size: 5,
      flag: true,
      showClickIcon: false,
      //data
    };
  },
  methods: {
    //鼠标移入事件
    enter(row) {
      console.log("鼠标移入事件");
      console.log("鼠标移入row：", row);
      this.showClickIcon = true;
      this.rowid = row.id; //赋值行id，便于页面判断
      this.rowData = row; //把行数据赋值，用于后续操作
      this.getLeaveTimes(row.userId);
      this.getOverTimes(row.userId);
    },
    //methods

    //重置
    resetForm() {
      console.log("调用重置");
      console.log(this.$options.data());
      this.ruleForm = this.$options.data().ruleForm;
    },
    //查询用户
    getUser() {
      this.$axios({
        url: "/user/getUser",
        method: "get",
      }).then((res) => {
        console.log(res.data);
        this.Users = res.data;
      });
    },
    //查询所有请假时间
    getLeaveTimes(userId) {
      console.log("查询数据库userId：", userId);
      this.$axios({
        url: "leave/getleaveTimes/" + userId,
        method: "post",
      }).then((res) => {
        this.leaveTimes = res.data;
      });
    },
    //查询所有加班时间
    getOverTimes(userId) {
      console.log("查询数据库userId：", userId);
      this.$axios({
        url: "workOvertime/getOverTimes/" + userId,
        method: "post",
      }).then((res) => {
        this.overtimeDays = res.data;
      });
    },
    //根据条件查询
    queryAttendanceInfo() {
      console.log("调用查询");
      if (this.ruleForm.userId == null) {
        this.$message({
          message: "请输入姓名",
          type: "warning",
        });
      }
      // else if (this.ruleForm.leaveTime.length == 0) {
      //   this.$message({
      //     message: "请输入日期",
      //     type: "warning",
      //   });
      // }
      else {
        // this.getTwoTime();
        console.log("调用根据条件查询", this.ruleForm);
        this.$axios({
          url: "queryAttendanceByConditions/" + this.ruleForm.userId,
          method: "post",
        }).then((res) => {
          console.log(res.data);
          if (res.data.length == 0) {
            this.$message.error("抱歉没有该人考勤");
            this.queryByPage(this.currentPage, this.size);
          } else {
            this.attendance = res.data;
            console.log("this.attendance:", this.attendance);
          }
        });
      }
    },
    //分页时修改每页的行数,这里会自动传入一个size
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      //修改当前每页的数据行数
      this.size = val;
      //数据重新分页
      this.queryByPage(this.currentPage, this.size);
    },
    //调整当前的页码
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      //修改当前的页码
      this.currentPage = val;
      //数据重新分页
      this.queryByPage(this.currentPage, this.size);
    },

    //通过分页查询用户
    queryByPage(currentPage, size) {
      this.$axios({
        url: "/getAttendanceAllByPage/" + currentPage + "/" + size,
        method: "post",
      }).then((res) => {
        console.log(res.data);
        //此页用户传值
        this.attendance = res.data.attendanceInfo;
        console.log(res.data.total);
        //用户数量
        this.total = res.data.total;
      });
    },
    //methods
  },
  mounted() {
    //mounted
    this.getUser();
    this.queryByPage(this.currentPage, this.size);

    // 判断用户管理员
    if (JSON.parse(window.localStorage.getItem("user" || "[]")).roleId == 1) {
      this.flag = true;
      console.log(this.flag);
    } else {
      this.flag = false;
      console.log(this.flag);
    }
    //mounted
  },
  //script
};
</script>

<style scoped>
.a {
  height: 60px;
  line-height: 50px;
  font-weight: 500;
  /* 设置显示为flex布局 */
  display: flex;
  /* 设置为flex左右布局 */
  justify-content: space-between;
}
.b {
  width: 100%;
  height: 150px;
  background-color: #fff;
  padding-top: 2%;
  border-radius: 15px;
  padding-left: 15px;
}
.c {
  width: 99.5%;
  margin-top: 1%;
  border: 1% solid rgb(255, 255, 255);
  background-color: #fff;
  border-radius: 15px;
  padding: 1%;
}
</style>