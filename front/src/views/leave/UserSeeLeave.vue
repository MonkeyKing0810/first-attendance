<template>
  <div>
    <div class="a">
      <span>查看请假单</span>
    </div>
    <div class="addLeave">
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item
          label="驳回原因："
          style="width: 700px"
          label-width="150px"
          size="mini"
        >
          <el-input
            :disabled="true"
            v-model="form.cancelThings"
            type="textarea"
          ></el-input>
        </el-form-item>
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
            v-if="form.leaveUrl !== '未上传'"
            style="width: 100px; height: 100px"
            :src="require('/src/image/' + form.leaveUrl)"
            :preview-src-list="srcList"
          >
          </el-image>
          <el-upload
            class="upload-demo"
            action="http://localhost:8091/file/upload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple="false"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">
              只能上传jpg/png文件，且不超过500kb
            </div>
          </el-upload>
        </el-form-item>
        <!-- 按钮样式一 -->
        <el-form-item
          label-width="150px"
          style="width: 700px"
          v-if="form.status === '已驳回'"
        >
          <el-button type="primary" @click="onSubmit()" size="mini"
            >确定</el-button
          >
          <el-button @click="cancel()" size="mini">取消</el-button>
        </el-form-item>
        <!-- 按钮样式二 -->
        <el-form-item label-width="150px" style="width: 700px" v-else>
          <el-button @click="cancel()" size="mini">返回</el-button>
        </el-form-item>
      </el-form>
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
        cancelThings: "",
      },

      row: [],
      form: {
        cancelThings: "",
        userName: "",
        leaveTypeName: "",
        leaveCounts: "",
        leaveThings: "",
        times: "",
        leaveUrl: "",
        leaveId: "",
        status: "",
      },

      showIf: null,
    };
  },
  methods: {
    //方法起点

    //上传图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 文件上传成功时的钩子
    handleAvatarSuccess(res, file) {
      this.form.leaveUrl = res;
      console.log(res);
      console.log(file);
    },
    getBack() {
      this.$router.push("/home/setLeave");
    },
    onSubmit() {
      console.log("点击确定按钮");

      this.$axios({
        url: "/leave/updateLeave",
        method: "put",
        data: {
          leaveId: this.form.leaveId,
          status: "未审批",
          leaveThings: this.form.leaveThings,
          leaveUrl: this.form.leaveUrl,
        },
      }).then((res) => {
        if (res.data == "修改成功") {
          this.$message({
            message: "重新申请成功",
            type: "success",
          });
          this.$router.push("/home/leave");
          this.queryByPage(this.currentPage, this.size);
        } else {
          this.$message.error("申请失败");
        }
      });
    },
    cancel() {
      this.$router.push("/home/leave");
    },

    //方法终点
  },
  created() {
    console.log("created");
  },
  mounted() {
    //字符串转JSON
    this.row = JSON.parse(this.$route.query.str);
    //页面跳转参数传
    this.form = this.row;
    console.log("form===row", this.form);
    // this.form.userName = this.row.userName;
    // this.form.leaveTypeName = this.row.leaveTypeName;
    // this.form.leaveCounts = this.row.leaveCounts + "天";
    // this.form.leaveThings = this.row.leaveThings;
    // this.leaveUrl = this.row.leaveUrl;
    // this.status = this.row.status;
    // this.leaveId = this.row.leaveId;
    // this.showIf = this.row.showIf;
    // console.log("hello");
    // console.log("传值原因：", this.row.cancelThings);
    // this.form.cancelThings = this.row.cancelThings;
    // // "/src/image/" +
    // this.form.times = this.row.times;

    console.log("mounted");

    console.log("转换成功:", JSON.parse(this.$route.query.str));

    console.log("路由传参：", this.form.userName);

    console.log("路由传参：", this.form.leaveTypeName);

    console.log("路由传参：", this.form.leaveCounts);

    console.log("路由传参：", this.form.leaveThings);

    console.log("路由传参：", this.form.leaveUrl);
    console.log("路由传参：", this.form.status);
    console.log("路由传参：", this.form.showIf);
    console.log("路由传参：", this.form.cancelThings);
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