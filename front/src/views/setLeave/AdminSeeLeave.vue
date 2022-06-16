<template>
  <div>
    <div class="a">
      <span>查看请假单</span>
    </div>
    <div class="addLeave">
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item
          label="姓名："
          style="width: 250px"
          label-width="150px"
          size="mini"
        >
          <el-input :disabled="true" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item
          label="请假类别："
          style="width: 260px"
          label-width="150px"
          size="mini"
        >
          <el-input :disabled="true" v-model="form.leaveTypeName"></el-input>
        </el-form-item>
        <el-form-item
          label="请假时间："
          label-width="150px"
          size="medium"
          style="width: 490px;align='right'"
        >
          <el-input :disabled="true" v-model="form.times"></el-input>
        </el-form-item>
        <el-form-item
          label="请假时长："
          prop="totalTime"
          label-width="150px"
          style="width: 260px"
        >
          <el-input v-model="form.leaveCounts" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item
          label="请假事由："
          label-width="150px"
          size="medium"
          style="width: 700px"
        >
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请填写请假事由"
            v-model="form.leaveThings"
            :disabled="true"
          >
          </el-input>
        </el-form-item>

        <el-form-item
          label="请假凭证："
          label-width="150px"
          size="medium"
          style="width: 400px; height: 200px"
        >
          <el-image
            v-if="leaveUrl !== '未上传'"
            style="width: 100px; height: 100px"
            :src="require('/src/image/' + leaveUrl)"
            :preview-src-list="srcList"
          >
          </el-image>
        </el-form-item>
        <el-form-item label-width="150px" style="width: 700px" v-if="showIf">
          <el-button type="primary" @click="onSubmit()" size="mini"
            >通过</el-button
          >
          <el-button @click="cancel()" size="mini">驳回</el-button>
        </el-form-item>
        <el-form-item label-width="150px" style="width: 700px" v-if="!showIf">
          <el-button @click="getBack()" size="mini">返回</el-button>
        </el-form-item>
      </el-form>
      <div style="width: 200px">
        <el-dialog
          title="驳回"
          :visible.sync="dialogVisible"
          width="40%"
          :lock-scroll="true"
          :center="false"
        >
          <el-form :model="resetForm" label-width="auto" class="form">
            <el-form-item label="驳回原因:" label-width="90px" size="medium">
              <el-input
                v-model="resetForm.cancelThings"
                type="textarea"
              ></el-input>
            </el-form-item>
          </el-form>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="sure()">确 定</el-button>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import { days } from "@/utils/days";
export default {
  data() {
    return {
      dialogVisible: false,
      resetForm: {
        cancelThings: null,
      },
      url: "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
      srcList: [
        "/src/image/d92d3a56bbd54dd486bd2cdd3a4badbf.jpg",
        "https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg",
      ],
      row: [],
      form: {
        userName: "",
        leaveTypeName: "",
        leaveCounts: "",
        leaveThings: "",
        times: "",
      },
      leaveUrl: "",
      leaveId: "",
      status: "",
      showIf: null,
    };
  },
  methods: {
    //方法起点
    getBack() {
      this.$router.push("/home/setLeave");
    },
    onSubmit() {
      console.log("点击通过按钮");
      this.status = "已审批";
      this.showIf = 0;
      this.$axios({
        url: "/leave/updateLeave",
        method: "put",
        data: {
          leaveId: this.leaveId,
          status: this.status,
          showIf: this.showIf,
        },
      }).then((res) => {
        console.log("通过审批请求：", res);
        if (res.data == "修改成功") {
          this.$message({
            message: "审批成功",
            type: "success",
          });
          this.$router.push("/home/setLeave");
        } else {
          this.$message.error("审批失败");
        }
      });
    },

    cancel() {
      this.dialogVisible = true;
      // 清除历史新增
      this.resetForm.cancelThings = null;
      console.log("弹窗是否开启", this.dialogVisible);
    },
    sure() {
      console.log("点击驳回按钮");
      if (this.resetForm.cancelThings == null) {
        this.$message({
          message: "请输填写驳回原因",
          type: "warning",
        });
      } else {
        this.status = "已驳回";
        this.$axios({
          url: "/leave/updateLeave",
          method: "put",
          data: {
            leaveId: this.leaveId,
            status: this.status,
            cancelThings: this.resetForm.cancelThings,
          },
        }).then((res) => {
          console.log("驳回审批请求：", res);
          if (res.data == "修改成功") {
            this.$message({
              message: "驳回成功",
              type: "success",
            });
            this.$router.push("/home/setLeave");
          } else {
            this.$message.error("驳回失败");
          }
        });
      }
    },

    //方法终点
  },

  // created() {
  //   console.log("created");
  //   this.row = JSON.parse(this.$route.params.str);
  //   this.form.userName = this.row.userName;
  //   this.form.leaveTypeName = this.row.leaveTypeName;
  //   this.form.leaveCounts = this.row.leaveCounts + "天";
  //   this.form.leaveThings = this.row.leaveThings;
  //   this.form.leaveUrl = "/src/image/" + this.row.leaveUrl;
  //   this.form.times = this.row.times;
  //   console.log("路由传参：", this.userName);

  //   console.log("路由传参：", this.leaveTypeName);

  //   console.log("路由传参：", this.leaveCounts);

  //   console.log("路由传参：", this.leaveThings);

  //   console.log("路由传参：", this.leaveUrl);
  // },
  mounted() {
    this.row = JSON.parse(this.$route.query.str);
    this.form.userName = this.row.userName;
    this.form.leaveTypeName = this.row.leaveTypeName;
    this.form.leaveCounts = this.row.leaveCounts + "天";
    this.form.leaveThings = this.row.leaveThings;
    this.leaveUrl = this.row.leaveUrl;
    this.status = this.row.status;
    this.leaveId = this.row.leaveId;
    this.showIf = this.row.showIf;
    // "/src/image/" +
    this.form.times = this.row.times;

    console.log("mounted");

    console.log("转换成功:", JSON.parse(this.$route.query.str));

    console.log("路由传参：", this.form.userName);

    console.log("路由传参：", this.form.leaveTypeName);

    console.log("路由传参：", this.form.leaveCounts);

    console.log("路由传参：", this.form.leaveThings);

    console.log("路由传参：", this.leaveUrl);
    console.log("路由传参：", this.status);
    console.log("路由传参：", this.showIf);
  },
};
</script>
<style scoped>
.addLeave {
  height: 100%;
  padding-top: 5%;
  background-color: rgb(250, 250, 250);
  border-radius: 15px;
}
.a {
  height: 60px;
  line-height: 50px;
  font-weight: 500;
  /* 设置显示为flex布局 */
  display: flex;
  /* 设置为flex左右布局 */
  justify-content: space-between;
}
</style>