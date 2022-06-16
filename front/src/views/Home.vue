<template>
  <el-container class="all">
    <el-header>
      <div>
        <span>考勤管理系统</span>
      </div>

      <el-dropdown trigger="click" style="font-size: 0.85em">
        <el-button type="primary" icon="el-icon-user" class="c-btn">
          {{ userName }}
        </el-button>

        <el-dropdown-menu slot="dropdown" split-button="true">
          <el-dropdown-item @click.native="updatePass" align="center">
            修改密码</el-dropdown-item
          >
          <el-dropdown-item divided @click.native="logout" align="center">
            退出
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-dialog
      title="修改密码"
      :visible.sync="dialogFormVisible"
      class="dialog"
      center="true"
      width="550px"
    >
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item label="原密码:" label-width="150px" size="medium">
          <label slot="label"
            ><span style="color: red">*</span>&nbsp;&nbsp;原密码:</label
          ><el-input v-model="form.password" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="新密码:" label-width="150px" size="medium">
          <label slot="label"
            ><span style="color: red">*</span>&nbsp;&nbsp;新密码:</label
          ><el-input v-model="form.newPassword" style="width: 200px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>
    <el-container class="allin">
      <el-aside width="200px">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
        >
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>考勤管理</span>
            </template>
            <el-menu-item index="1-1" @click="getLeave()"
              >请假管理</el-menu-item
            >
            <el-menu-item index="1-2" v-if="flag" @click="setLeave()"
              >批假管理</el-menu-item
            >
            <el-menu-item index="1-3" @click="workOvertime()"
              >加班管理</el-menu-item
            >
            <el-menu-item index="1-4" @click="attendance()"
              >考勤统计</el-menu-item
            >
          </el-submenu>
          <el-submenu index="2" v-if="flag">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item index="2-1" @click="getUser()">用户管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      flag: "",
      pass: null,
      userName: JSON.parse(window.localStorage.getItem("user")).username,
      form: {
        userId: JSON.parse(window.localStorage.getItem("user")).userId,

        password: null,
        newPassword: null,
        roleId: null,
      },
      //弹窗
      dialogFormVisible: false,
    };
  },

  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    updatePass() {
      this.getUserById();
      this.form.password = null;
      this.form.newPassword = null;
      this.dialogFormVisible = true;
    },
    getUserById() {
      this.$axios({
        url: "/user/getUserById/" + this.form.userId,
        method: "post",
      }).then((res) => {
        this.pass = res.data.password;
        console.log("原密码：", res.data.password);
        console.log("接收到原密码", this.pass);
      });
      console.log("接收到原密码22222", this.pass);
    },
    sureOrUpdate() {
      console.log("返回的原密码", this.pass);
      console.log("输入的原密码：", this.form.password);

      console.log("输入的新密码：", this.form.newPassword);

      if (this.form.password === this.pass) {
        this.$axios({
          url: "/user/updateUser",
          method: "put",
          data: {
            userId: this.form.userId,
            password: this.form.newPassword,
          },
        }).then((res) => {
          console.log("修改密码");
          if (res.data == "修改成功") {
            this.dialogFormVisible = false;
            this.$message({
              message: "修改成功",
              type: "success",
            });
          } else {
            this.$message.error("修改失败");
          }
        });
      } else {
        this.$message({
          message: "原密码错误！",
          type: "warning",
        });
      }
    },
    logout() {
      // 这里需要elementui的支持，如果使用其他界面组件自行替换即可
      this.$confirm("正在离开本页面，本页面内所有未保存数据都会丢失", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //清除用户缓存
        window.localStorage.clear();
        this.$router.push("/login");
      });
    },
    getLeave() {
      this.$router.push("/home/leave");
    },
    setLeave() {
      this.$router.push("/home/setLeave");
    },
    workOvertime() {
      this.$router.push("/home/workOvertime");
    },
    attendance() {
      this.$router.push("/home/attendance");
    },
    getUser() {
      this.$router.push("/home/user");
    },
  },
  mounted() {
    // 判断用户管理员
    if (JSON.parse(window.localStorage.getItem("user" || "[]")).roleId == 1) {
      this.flag = true;
      console.log(this.flag);
    }
    console.log(JSON.parse(window.localStorage.user).roleId);
  },
};
</script>

<style scoped>
.all {
  height: 100%;
  width: 100%;
  margin: 0%;
  padding: 0%;
}
.allin {
  height: 100%;
  width: 100%;
  margin: 0%;
  padding: 0%;
  border-top: 1px solid rgb(88, 87, 87);
}
.el-header {
  background-color: #373d41;
  /* 设置显示为flex布局 */
  display: flex;
  /* 设置为flex左右布局 */
  justify-content: space-between;
  /* 左内边距为0（Logo贴左边） */
  padding-left: 0;
  /* 元素上下居中（防止右边按钮贴上下边） */
  align-items: center;
  color: #fff;
  font-size: 20px;
}
/deep/.el-dialog {
  border-radius: 15px;
}
.el-header > div {
  /* 内嵌的div样式 */
  display: flex;
  /* Logo和文字上下居中 */
  align-items: center;
}
span {
  /* 文字左侧设置间距，防止与Logo紧贴 */
  margin-left: 15px;
}
.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
  width: 100%;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 55px;
}
</style>
