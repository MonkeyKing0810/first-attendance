<template>
  <div>
    <div class="a">
      <span>用户管理</span>
      <el-button type="primary" style="height: 40px" @click="addUser()"
        >新建用户</el-button
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
        <el-form-item label="姓名:" label-width="150px" size="medium">
          <el-input v-model="form.userName" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="联系方式：" label-width="150px" size="medium">
          <el-input v-model="form.linkNum" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="密码：" label-width="150px" size="medium">
          <el-input v-model="form.password" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item
          label="角色"
          style="width: 300px"
          label-width="150px"
          size="mini"
        >
          <el-select
            v-model="form.roleId"
            placeholder="请选择职位"
            style="width: 200px"
          >
            <el-option
              v-for="item in roleInfo"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
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
        style="width: 400px"
        :model="ruleForm"
      >
        <el-form-item style="margin-left=0px" prop="pass">
          姓名：<el-input
            v-model="ruleForm.username"
            placeholder="请输入姓名"
            fixed="true"
            style="width: 120px"
          >
          </el-input>
          联系方式：<el-input
            v-model="ruleForm.linknum"
            placeholder="请输入联系方式"
            style="width: 150px"
          >
          </el-input>
        </el-form-item>
        <el-form-item style="width: 180px; height: 30px">
          <el-button
            type="primary"
            icon="el-icon-search"
            class="querybutton"
            @click="queryUserInfo()"
            >查询</el-button
          >
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 数据展示表格 -->
    <div class="c">
      <el-table :data="Users" :header-cell-style="{ color: 'red' }">
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column prop="userName" label="姓名" width="100">
        </el-table-column>
        <el-table-column prop="linkNum" label="联系方式" width="120">
        </el-table-column>
        <el-table-column prop="roleName" label="角色" width="120">
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="updateUser(scope.row)" type="text" size="small"
              >编辑</el-button
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
    </div>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="width: 120px"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Users: [],
      ruleForm: {
        username: null,
        linknum: null,
      },
      form: {
        userId: null,
        userName: null,
        linkNum: null,
        password: null,
        roleId: null,
      },
      //弹窗
      dialogFormVisible: false,
      roleInfo: [],
      currentPage: 1,
      total: "",
      size: 5,
    };
  },
  methods: {
    handleClick(row) {
      console.log(row);
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
        url: "/user/getUserAllByPage/" + currentPage + "/" + size,
        method: "post",
      }).then((res) => {
        console.log(res.data);
        //此页用户传值
        this.Users = res.data.userInfo;
        console.log(res.data.total);
        //用户数量
        this.total = res.data.total;
      });
    },
    //根据姓名和联系方式查询用户
    queryUserInfo() {
      if (this.ruleForm.username == null) {
        this.$message({
          message: "请输入姓名",
          type: "warning",
        });
      } else if (this.ruleForm.linknum == null) {
        this.$message({
          message: "请输入联系方式",
          type: "warning",
        });
      } else {
        this.$axios({
          url:
            "/user/queryUserByConditions/" +
            this.ruleForm.username +
            "/" +
            this.ruleForm.linknum,
          method: "post",
        }).then((res) => {
          console.log(res.data.length);
          if (res.data.length == 0) {
            this.$message.error("抱歉没有次用户");
            this.queryByPage(this.currentPage, this.size);
          }
          this.Users = res.data;
        });
      }
    },
    // 查询所有职业
    getRoleAll() {
      this.$axios({
        url: "/role/getRoleAll",
        method: "get",
      }).then((res) => {
        this.roleInfo = res.data;
      });
    },
    //判断是增加还是修改
    sureOrUpdate() {
      //判断userId是否存在  存在则为修改
      console.log(this.form.userId);
      if (this.form.userId) {
        this.updateUserById();
      } else {
        this.saveUser();
      }
    },
    //     先调用按钮修改dialogFormVisible为true
    // 才能打开弹窗
    // 添加用户
    addUser() {
      this.dialogTitle = "新建用户";
      this.dialogFormVisible = true;
      // 清除历史新增
      // 注意userId必须在新增前清除  不然会影响新增信息的userId
      this.form.userId = null;
      this.form.userName = null;
      this.form.linkNum = null;
      this.form.password = null;
      this.form.roleId = null;
    },
    saveUser() {
      this.$axios({
        url: "/user/insertUser",
        method: "post",
        data: {
          userName: this.form.userName,
          linkNum: this.form.linkNum,
          password: this.form.password,
          roleId: this.form.roleId,
        },
      }).then((res) => {
        if (res.data == "新增成功") {
          this.dialogFormVisible = false;
          this.$message({
            message: "新增成功",
            type: "success",
          });
          this.queryByPage(this.currentPage, this.size);
        } else {
          this.$message.error("新增失败");
        }
      });
    },
    updateUser(row) {
      console.log(row);
      console.log(row);
      this.dialogTitle = "编辑用户";
      (this.form.userId = row.userId),
        (this.form.userName = row.userName),
        (this.form.linkNum = row.linkNum),
        (this.form.password = row.password),
        (this.form.roleId = row.roleId),
        (this.dialogFormVisible = true);
    },
    updateUserById() {
      this.$axios({
        url: "/user/updateUser",
        method: "put",
        data: {
          userId: this.form.userId,
          userName: this.form.userName,
          linkNum: this.form.linkNum,
          password: this.form.password,
          roleId: this.form.roleId,
        },
      }).then((res) => {
        if (res.data == "修改成功") {
          this.dialogFormVisible = false;
          this.$message({
            message: "修改成功",
            type: "success",
          });
          this.queryByPage(this.currentPage, this.size);
        } else {
          this.$message.error("失败");
        }
      });
    },

    //单个删除
    deleteUserById(row) {
      this.$confirm("确定删除词条记录吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios({
            url: "/user/deleteUserById/" + row.userId,
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
    this.getRoleAll();
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
.a > span {
  text-align: left;
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
.el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /*height:600px;*/
  max-height: calc(100% - 200px);
  max-width: calc(100% - 30px);
}
.el-dialog .el-dialog__body {
  flex: 1;
  overflow: auto;
}
</style>