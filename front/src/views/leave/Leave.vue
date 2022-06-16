<template>
  <div>
    <div class="a">
      <span>请假管理</span>

      <el-button type="primary" style="height: 40px" @click="addLeave()"
        >新建请假单</el-button
      >
    </div>
    <!--弹窗表单-->
    <!-- dialogFormVisible控制弹窗true为弹出 false为消失 -->
    <!-- width="550px" 可控制整个表单随页面变化居中 -->
    <!-- center="true"使表单 头（标题）  尾（取消和确定按钮）居中 -->
    <!-- size="mini"  size="medium" 控制整个el-form-item大小中 -->
    <!-- :title="dialogTitle" 表头设置变量 方便根据不同方法修改表头 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogFormVisible"
      class="dialog"
      center="true"
      width="550px"
    >
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item
          label="请假类别"
          style="width: 300px"
          label-width="150px"
          size="mini"
        >
          <el-select
            v-model="status"
            placeholder="请选择状态"
            style="width: 200px"
          >
            <el-option
              v-for="item in status"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="请假时间：" label-width="150px" size="medium">
          <el-col :span="11">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.leaveTime"
              style="width: 150px"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-col>
          <!-- <el-col class="line" :span="2">-</el-col>
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="form.date1"
            style="width: 150px"
          ></el-date-picker> -->
        </el-form-item>

        <el-form-item label="请假事由：" label-width="150px" size="medium">
          <el-input v-model="form.leaveThings" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item
          label="状态"
          style="width: 300px"
          label-width="150px"
          size="mini"
        >
          <el-select
            v-model="form.status"
            placeholder="请选择状态"
            style="width: 200px"
          >
            <el-option
              v-for="item in status"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 条件查询表格 -->
    <div class="b">
      <el-form
        label-width="margin-left=0px"
        style="width: 650px"
        :model="ruleForm"
      >
        <el-form-item prop="pass" :inline-message="true">
          请假类别：<el-select
            v-model="ruleForm.leaveTypeId"
            placeholder="请选择"
            style="width: 100px"
          >
            <el-option
              v-for="item in LeaveType"
              :key="item.leaveTypeId"
              :label="item.leaveTypeName"
              :value="item.leaveTypeId"
            >
            </el-option>
          </el-select>

          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请假时间：
          <el-date-picker
            v-model="ruleForm.leaveTime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>

          <!-- <el-col class="line" :span="2">-</el-col>
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="form.date1"
            style="width: 150px"
          ></el-date-picker> -->
        </el-form-item>

        <el-form-item style="width: 180px; height: 30px">
          <el-button
            type="primary"
            icon="el-icon-search"
            class="querybutton"
            @click="queryLeaveInfo()"
            >查询</el-button
          >
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 数据展示表格 -->
    <div class="c">
      <el-table :data="Leaves" :header-cell-style="{ color: 'red' }">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>
        <el-table-column prop="leaveTypeName" label="请假类别" width="100">
        </el-table-column>
        <el-table-column
          label="请假时间"
          width="300"
          align="center"
          prop="times"
        >
        </el-table-column>
        <el-table-column prop="status" label="状态" width="150">
        </el-table-column>
        <el-table-column prop="leaveThings" label="请假事由" width="300">
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button
              v-if="
                scope.row.status === '已审批' || scope.row.status === '未审批'
              "
              @click="handleClick(scope.row)"
              type="text"
              size="small"
              >查看</el-button
            >
            <el-button
              v-else
              size="small"
              type="text"
              @click="handleClick(scope.row)"
              >编辑</el-button
            >
            <el-button
              v-if="
                scope.row.status === '已驳回' || scope.row.status === '未审批'
              "
              type="text"
              size="small"
              @click="deleteLeaveById(scope.row)"
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
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Leaves: [],
      LeaveType: [],
      ruleForm: {
        leaveTypeId: null,
        leaveTime: [],
      },
      startTime: "",
      entTime: "",
      status: [
        {
          value: "选项1",
          label: "待审批",
        },
        {
          value: "选项2",
          label: "已审批",
        },
        {
          value: "选项3",
          label: "已驳回",
        },
      ],
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },

      form: {
        leaveId: "",
        leaveTypeId: "",
        leaveTime: "",
        leaveCounts: "",
        status: "",
        leaveThings: "",
      },
      //弹窗
      dialogFormVisible: false,
      currentPage: 1,
      total: 15,
      size: 5,
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
    //查看请假单跳转
    handleClick(row) {
      console.log("执行查看");
      console.log(row);
      this.$router.push({
        name: "userSeeLeave",
        query: {
          str: JSON.stringify(row),
        },
      });
      (this.str = JSON.stringify(row)), console.log("str:", this.str);
    },
    //重置
    resetForm() {
      console.log("调用重置");
      console.log(this.$options.data());
      this.ruleForm = this.$options.data().ruleForm;
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
        url: "/leave/getLeaveAllByPage/" + currentPage + "/" + size,
        method: "post",
      }).then((res) => {
        console.log(res.data);
        //此页用户传值
        this.Leaves = res.data.leaveInfo;
        console.log("leaves数组", res.data.leaveInfo);
        //用户数量
        this.total = res.data.total;
      });
    },
    //查询请假类别表
    queryLeaveType() {
      this.$axios({
        url: "/leaveType/getLeaveTypeAll",
        method: "get",
      }).then((res) => {
        this.LeaveType = res.data;
      });
    },

    //新建请假单跳转
    addLeave() {
      this.$router.push("/home/addLeave");
    },
    //daterange 拆分
    getTwoTime() {
      if (this.ruleForm.leaveTime != null) {
        (this.startTime = this.ruleForm.leaveTime[0]),
          (this.entTime = this.ruleForm.leaveTime[1]);
      }
      console.log(this.startTime);
      console.log(this.entTime);
    },
    //根据请假类别和请假时间查询请假单
    queryLeaveInfo() {
      if (this.ruleForm.leaveTypeId == null) {
        this.$message({
          message: "请输入请假类别名",
          type: "warning",
        });
        this.queryByPage(this.currentPage, this.size);
      } else if (this.ruleForm.leaveTime.length == 0) {
        this.$message({
          message: "请输入日期",
          type: "warning",
        });
        this.queryByPage(this.currentPage, this.size);
      } else {
        this.getTwoTime();
        this.$axios({
          url:
            "/leave/queryLeaveByConditions/" +
            this.ruleForm.leaveTypeId +
            "/" +
            this.startTime +
            "/" +
            this.entTime,
          method: "post",
        }).then((res) => {
          console.log(res.data.length);
          if (res.data.length == 0) {
            this.$message.error("抱歉没有次请假单");
            this.queryByPage(this.currentPage, this.size);
          }
          this.Leaves = res.data;
        });
      }
    },

    //单个删除
    deleteLeaveById(row) {
      this.$confirm("是否删除该请假单?", "系统消息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios({
            url: "/leave/deleteLeaveById/" + row.leaveId,
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
  },
  mounted() {
    this.queryByPage(this.currentPage, this.size);
    this.queryLeaveType();
    console.log("验证用户权限判断返回值", this.isAdmit());
  },
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