<template>
  <div>
    <div class="a">
      <span>加班管理</span>
      <el-button type="primary" style="height: 40px" @click="addWorkOvertime()"
        >新建加班</el-button
      >
    </div>
    <!--弹窗表单-->
    <!-- dialogFormVisible控制弹窗true为弹出 false为消失 -->
    <!-- width="550px" 可控制整个表单随页面变化居中 -->
    <!-- center="true"使表单 头（标题）  尾（取消和确定按钮）居中 -->
    <!-- size="mini"  size="medium" 控制整个el-form-item大小中 -->
    <!-- :title="dialogTitle" 表头设置变量 方便根据不同方法修改表头 -->
    <el-dialog
      title="新建加班"
      :visible.sync="dialogFormVisible"
      class="dialog"
      center="true"
      width="550px"
    >
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item label="加班时间:" label-width="100px" size="medium">
          <el-date-picker
            v-model="form.workOvertimeTime"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 400px"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="加班原因:" label-width="100px" size="medium">
          <el-input
            v-model="form.workOvertimeThings"
            style="width: 400px"
            type="textarea"
            placeholder="请输入加班原因"
            :rows="4"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 条件查询表格 -->
    <div class="b" v-if="flag">
      <el-form style="width: 600px" :model="ruleForm">
        <el-form-item prop="pass" :inline-message="true">
          姓名：<el-select
            v-model="ruleForm.userName"
            placeholder="请选择"
            style="width: 100px"
            @change="towerChange"
          >
            <el-option
              v-for="item in Users"
              :key="item.userId"
              :label="item.userName"
              :value="item"
            >
            </el-option>
          </el-select>

          &nbsp;&nbsp;&nbsp;&nbsp;加班时间：
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
            @click="queryWorkOvertimeInfo()"
            >查询</el-button
          >
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 条件查询表格(员工) -->
    <div class="b" v-if="!flag">
      <el-form style="width: 440px" :model="ruleForm">
        <el-form-item prop="pass" :inline-message="true">
          &nbsp;&nbsp;&nbsp;&nbsp;加班时间：
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
            @click="queryWorkOvertimeUserInfo()"
            >查询</el-button
          >
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 数据展示表格 -->
    <div class="c" v-if="flag">
      <el-table :data="workOvertime" :header-cell-style="{ color: 'red' }">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>

        <el-table-column prop="userName" label="姓名" width="100">
        </el-table-column>

        <el-table-column
          prop="workOvertimeTime"
          label="加班时间"
          width="300"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="workOvertimeThings"
          label="加班原因"
          width="200"
          align="center"
        >
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="updateUser(scope.row)" type="text" size="small"
              >查看</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="deleteUserById(scope.row)"
              >删除</el-button
            >
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
          style="width: 120px"
        >
        </el-pagination>
      </div>
    </div>
    <!-- 数据展示表格(员工) -->
    <div class="c" v-if="!flag">
      <el-table :data="workOvertime" :header-cell-style="{ color: 'red' }">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>

        <el-table-column
          prop="workOvertimeTime"
          label="加班时间"
          width="300"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="workOvertimeThings"
          label="加班原因"
          width="200"
          align="center"
        >
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="updateUser(scope.row)" type="text" size="small"
              >查看</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="deleteUserById(scope.row)"
              >删除</el-button
            >
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
          style="width: 120px"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { days } from "@/utils/days";
export default {
  //script
  data() {
    return {
      //data
      Users: [],
      workOvertime: [],
      dialogFormVisible: false,
      form: {
        workOvertimeTime: [],
        workOvertimeThings: "",
        overDays: "",
      },
      startTime: "",
      endTime: "",
      ruleForm: {
        userId: null,
        userName: null,
        leaveTypeId: null,
        leaveTime: [],
        status: null,
      },
      currentPage: 1,
      total: "",
      size: 5,
      flag: false,
      userId: JSON.parse(window.localStorage.getItem("user")).userId,
      //data
    };
  },
  methods: {
    //当前登录用户是否有权限访问内容
    isAdmit() {
      if (JSON.parse(window.localStorage.getItem("user" || "[]")).roleId == 1) {
        return true;
      } else {
        return false;
      }
    },
    //methods
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
    //通过分页查询加班信息
    queryByPage(currentPage, size) {
      console.log("进入分页", this.isAdmit());
      if (this.isAdmit()) {
        console.log("进入了if查询所有用户信息");
        this.$axios({
          url:
            "/workOvertime/getWorkOverTimeAllByPage/" +
            currentPage +
            "/" +
            size,
          method: "post",
        }).then((res) => {
          console.log(res.data);
          //此页传值
          this.workOvertime = res.data.workOvertimeInfo;
          console.log(res.data.total);
          //数量
          this.total = res.data.total;
        });
      } else {
        console.log("进入了else查询用户信息");
        console.log("userId:", this.userId);
        this.$axios({
          url:
            "/workOvertime/getWorkOverTimeUserAllByPage/" +
            this.userId +
            "/" +
            currentPage +
            "/" +
            size,
          method: "post",
        }).then((res) => {
          console.log(res.data);

          //此页传值
          this.workOvertime = res.data.workOvertimeUserInfo;
          //去除null数据
          // if (this.workOvertime) {
          //   this.workOvertime.forEach((element) => {
          //     for (var key in element) {
          //       if (element[key] == null) {
          //         delete this.workOvertime[element];
          //       }
          //     }
          //   });
          // }
          // console.log("去除null后的数组", this.workOvertime);
          console.log(res.data.total);
          //数量
          this.total = res.data.total;
        });
      }
    },

    //弹窗
    addWorkOvertime() {
      console.log("调用弹窗");
      this.dialogFormVisible = true;
      // 清除历史新增
      this.form.workOvertimeTime = null;
      this.form.workOvertimeThings = null;
    },

    //新建加班
    sureOrUpdate() {
      console.log("点击确定按钮");
      console.log(this.form.workOvertimeTime);
      console.log(this.form.workOvertimeTime[0]);
      var moment = require("moment");
      if (
        this.form.workOvertimeTime == null ||
        this.form.workOvertimeThings == null
      ) {
        alert("请补全信息！");
      } else {
        this.$axios({
          url: "workOvertime/insertWorkOverTime",
          method: "post",
          data: {
            userId: JSON.parse(window.localStorage.getItem("user")).userId,
            startTime: moment(this.form.workOvertimeTime[0]).format(
              "YYYY-MM-DD HH:mm:ss"
            ),
            endTime: moment(this.form.workOvertimeTime[1]).format(
              "YYYY-MM-DD HH:mm:ss"
            ),
            overDays: days(
              this.form.workOvertimeTime[0],
              this.form.workOvertimeTime[1]
            ),
            workOvertimeThings: this.form.workOvertimeThings,
          },
        }).then((res) => {
          if (res.data == "新增成功") {
            this.$message({
              message: "新增成功",
              type: "success",
            });
            this.$router.push("/home/workOvertime");
            this.queryByPage(this.currentPage, this.size);
            this.dialogFormVisible = false;
          } else {
            this.$message.error("新增失败");
          }
        });
      }
    },

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
    towerChange(e) {
      //获取的值和id
      this.ruleForm.userId = e.userId;
      this.ruleForm.userName = e.userName;
    },
    //根据条件查询
    queryWorkOvertimeInfo() {
      console.log("调用查询");
      if (this.ruleForm.userName == null) {
        this.$message({
          message: "请输入姓名",
          type: "warning",
        });
      } else if (this.ruleForm.leaveTime.length == 0) {
        this.$message({
          message: "请输入加班时间",
          type: "warning",
        });
      } else {
        // this.getTwoTime();
        console.log("ruleFrom", this.ruleForm);

        console.log("userId", this.ruleForm.userId);
        console.log("userName", this.ruleForm.userName);
        this.$axios({
          url:
            "/workOvertime/queryWorkOvertimeByConditions/" +
            this.ruleForm.userName +
            "/" +
            this.ruleForm.leaveTime[0] +
            "/" +
            this.ruleForm.leaveTime[1],
          method: "post",
        }).then((res) => {
          console.log("res", res.data);
          if (res.data.length == 0) {
            this.$message.error("抱歉没有次加班");
            this.queryByPage(this.currentPage, this.size);
          }
          console.log(res.data);
          this.workOvertime = res.data;
        });
      }
    },
    //根据条件查询(员工)
    queryWorkOvertimeUserInfo() {
      console.log("调用查询");

      if (this.ruleForm.leaveTime.length == 0) {
        this.$message({
          message: "请输入加班时间",
          type: "warning",
        });
        this.queryByPage(this.currentPage, this.size);
      } else {
        this.$axios({
          url:
            "/workOvertime/queryUserWorkOvertimeByConditions/" +
            this.ruleForm.leaveTime[0] +
            "/" +
            this.ruleForm.leaveTime[1],
          method: "post",
        }).then((res) => {
          console.log("res", res.data);
          if (res.data.length == 0) {
            this.$message.error("抱歉没有次加班");
            this.queryByPage(this.currentPage, this.size);
          }
          console.log(res.data);
          this.workOvertime = res.data;
        });
      }
    },
    //单个删除
    deleteUserById(row) {
      this.$confirm("是否确定删除该加班?", "系统消息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios({
            url: "/workOvertime/deleteWorkOverTimeById/" + row.workOvertimeId,
            method: "delete",
          }).then((res) => {
            console.log(res);
            if (res.data == "删除成功") {
              this.$message({
                message: "删除成功",
                type: "success",
              });
              this.queryByPage(this.currentPage, this.size);
            } else {
              this.$message.error("删除失败");
            }
          });
          console.log(row.userId);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //methods
  },

  mounted() {
    //mounted
    this.getUser();
    this.queryByPage(this.currentPage, this.size);
    console.log(JSON.parse(window.localStorage.getItem("user")).userId);

    // 判断用户管理员
    if (JSON.parse(window.localStorage.getItem("user" || "[]")).roleId == 1) {
      this.flag = true;
      console.log(this.flag);
    }
    console.log("roleId:", JSON.parse(window.localStorage.user).roleId);

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
</style>