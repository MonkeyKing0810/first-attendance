<template>
  <div>
    <el-form
      :rules="rules"
      class="login-container"
      label-position="left"
      label-width="0px"
      v-loading="loading"
    >
      <img class="pic" src="../image/1653468306(1).jpg" />
      <div class="from">
        <h3 class="login_title" style="height: 20px">
          欢迎使用<br />考勤管理系统
        </h3>
        <el-form-item prop="account">
          联系方式：<el-input
            style="width: 300px"
            type="text"
            v-model="loginForm.username"
            auto-complete="off"
            placeholder="账户"
          ></el-input>
        </el-form-item>

        <el-form-item prop="checkpass">
          密码：<el-input
            style="width: 300px; margin-left: 25px"
            type="password"
            v-model="loginForm.password"
            auto-complete="off"
            placeholder="密码"
          ></el-input>
        </el-form-item>

        <el-form-item style="width: 100%">
          <el-button
            type="primary"
            style="width: 75%; margin-left: 68px"
            @click="submitClick"
            @keyup.enter="submitClick"
            >登录</el-button
          >
        </el-form-item>
        <el-checkbox
          class="login_remember"
          v-model="checked"
          label-position="left"
          >记住密码</el-checkbox
        >
      </div>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // rules: {
      //   account: [{ required: true, message: "请输入账号", trigger: "blur" }],
      //   checkpass: [{ required: true, message: "请输入密码", trigger: "blur" }],
      // },
      loginForm: {
        userId: "",
        username: null,
        password: null,
        roleId: 5,
      },

      checked: true,
      loading: false,
      responseResult: [],
    };
  },
  methods: {
    submitClick() {
      if (this.loginForm.username == null) {
        alert("请输入账号！");
      } else if (this.loginForm.password == null) {
        alert("请输入密码！");
        //重置
        this.ruleForm = this.$options.data().loginForm;
      } else {
        console.log("获取界面输入的登录信息，准备向后端接口传入数据");
        console.log(this.$store.state);
        this.$axios({
          url: "/user/login",
          method: "post",
          data: {
            linkNum: this.loginForm.username,
            password: this.loginForm.password,
          },
        }).then((res) => {
          console.log(res.data);
          this.loginForm.roleId = res.data.roleId;
          this.loginForm.userId = res.data.userId;
          console.log(res.data.roleId);
          console.log(res.data.userId);
          if (res.data.msg == "登录成功") {
            alert("登录成功");
            //这里是验证成功的地方，给user传值,传到mutations中
            this.$store.commit("login", this.loginForm);
            var path = this.$route.query.redirect;
            this.$router.push({
              path: path === "/" || path === undefined ? "/home" : path,
            });
          } else {
            alert("用户名或密码错误");
          }
        });
      }
    },
    // 点击回车键登录
    keyDown(e) {
      // 回车则执行登录方法 enter键的ASCII是13
      if (e.keyCode === 13) {
        this.submitClick(); // 定义的登录方法
      }
    },
  },
  mounted() {
    // 绑定监听事件
    window.addEventListener("keydown", this.keyDown);
  },
  destroyed() {
    // 销毁事件
    window.removeEventListener("keydown", this.keyDown, false);
  },
};
</script>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin-left: -5%;
  width: 100vw;
  padding: 75px 35px 5px 35px;
  background: #fff;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: blue;
}
.login_remember {
  margin: 0px 0px 15px 0px;
  text-align: left;
}
.pic {
  display: inline-block;
}
.from {
  display: inline-block;
  margin-left: 50px;
  width: 400px;
}
</style>