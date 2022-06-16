<template>
  <div>
    <div class="a">
      <span>新建请假单</span>
    </div>
    <div class="addLeave">
      <el-form ref="form" :model="form" label-width="auto" class="form">
        <el-form-item
          label="姓名："
          style="width: 250px"
          label-width="150px"
          size="mini"
        >
          <el-input :disabled="true" v-model="userName"></el-input>
        </el-form-item>
        <el-form-item
          label="请假类别："
          style="width: 260px"
          label-width="150px"
          size="mini"
        >
          <el-select v-model="form.leaveTypeId" placeholder="请选择">
            <el-option
              v-for="item in LeaveType"
              :key="item.leaveTypeId"
              :label="item.leaveTypeName"
              :value="item.leaveTypeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="请假时间："
          label-width="150px"
          size="medium"
          style="width: 490px;align='right'"
        >
          <el-date-picker
            v-model="leaveTime"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="请假时长："
          prop="totalTime"
          label-width="150px"
          style="width: 260px"
        >
          <el-input
            v-model="getTim"
            :disabled="true"
            placeholder="请假时长，单位秒"
          ></el-input>
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
          style="width: 400px"
        >
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
        <el-form-item label-width="150px" style="width: 700px">
          <el-button type="primary" @click="onSubmit()" size="mini"
            >确定</el-button
          >
          <el-button @click="cancel()" size="mini">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getTime } from "@/utils/dateUtils";
import { isNull } from "@/utils/validate";
import { days } from "@/utils/days";
export default {
  data() {
    return {
      fileList: [],
      form: {
        totalTime: "",
        leaveTypeId: "",
        leaveCounts: "",
        leaveThings: "未填写",
        leaveUrl: "未上传",
      },
      startTime: "",
      endTime: "",
      status: "",
      showIf: null,
      leaveTime: [],

      LeaveType: [],

      userName: JSON.parse(window.localStorage.getItem("user")).username,
    };
  },
  methods: {
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
    // handleelchange(file, fileList) {
    //   console.log(file, fileList);
    //   let formdata = new FormData();
    //   fileList.map((item) => {
    //     //fileList本来就是数组，就不用转为真数组了
    //     formdata.append("file", item.raw); //将每一个文件图片都加进formdata
    //   });
    //   this.$axios("接口地址", formdata).then((res) => {
    //     console.log(res);
    //   });
    // },

    //     不用设置请求头等（直接用FormData格式提交就行），当看到formdata数据为二进制就说明提交正常（binary），同样有些浏览器不显示binary，以………
    // WebKitFormBoundaryXoZpi2juymcNoW0l开头的这种也是提交正常的

    // 注意fleList数组中的raw才是真实文件数据，如果直接用item而不是item.raw，那么会报500错误，并且formdata中的数据不是二进制而是对象格式
    // filoList.map（item=>{
    // formdata.append（“file”，item）//错误实例。要用tem.raw
    // https://wenku.baidu.com/view/ee16df93fe0a79563c1ec5da50e2524de518d0ac.html
    //查询请假类别表
    queryLeaveType() {
      this.$axios({
        url: "/leaveType/getLeaveTypeAll",
        method: "get",
      }).then((res) => {
        this.LeaveType = res.data;
      });
    },
    //daterange 拆分
    getTwoTime() {
      console.log(this.leaveTime);
      if (this.leaveTime != null) {
        (this.startTime = this.leaveTime[0]),
          (this.endTime = this.leaveTime[1]);
      }
      console.log(this.startTime);
    },
    onSubmit() {
      console.log("点击确定按钮");
      if (this.form.leaveTypeId == "" || this.leaveTime.length == 0) {
        alert("请补全信息！");
      } else {
        this.getTwoTime();
        var moment = require("moment");
        //权限为管理员时，请假后不需要审批
        if (JSON.parse(window.localStorage.getItem("user")).roleId == 1) {
          this.status = "已审批";
          this.showIf = false;
        } else {
          this.status = "未审批";
          this.showIf = true;
        }

        this.$axios({
          url: "leave/insertLeave",
          method: "post",
          data: {
            leaveTypeId: this.form.leaveTypeId,
            startTime: moment(this.startTime).format("YYYY-MM-DD HH:mm:ss"),
            endTime: moment(this.endTime).format("YYYY-MM-DD HH:mm:ss"),
            leaveCounts: days(this.startTime, this.endTime),
            leaveThings: this.form.leaveThings,
            leaveUrl: this.form.leaveUrl,
            status: this.status,
            showIf: this.showIf,
            userId: JSON.parse(window.localStorage.getItem("user")).userId,
          },
        }).then((res) => {
          if (res.data == "新增成功") {
            this.$message({
              message: "新增成功",
              type: "success",
            });
            console.log(days(this.startTime, this.endTime));
            this.$router.push("/home/leave");
            this.queryByPage(this.currentPage, this.size);
          } else {
            this.$message.error("新增失败");
          }
        });
      }
    },
    cancel() {
      this.$router.push("/home/leave");
    },
  },
  computed: {
    getTim: function () {
      let startTime;
      let endTime;
      console.log("isNull???");
      if (isNull(this.leaveTime)) {
        startTime = new Date();
        endTime = new Date();
        console.log("isNull");
      } else {
        startTime = isNull(this.leaveTime[0]) ? new Date() : this.leaveTime[0];
        endTime = isNull(this.leaveTime[1]) ? new Date() : this.leaveTime[1];
        console.log(getTime(startTime, endTime));
        console.log("computed");
      }
      return getTime(startTime, endTime);
    },
  },
  mounted() {
    console.log("mounted");
    this.queryLeaveType();
    console.log("获取id", JSON.parse(window.localStorage.getItem("user")));
    console.log(
      "获取id",
      JSON.parse(window.localStorage.getItem("user")).userId
    );
    console.log(
      "获取当前权限",
      JSON.parse(window.localStorage.getItem("user")).roleId
    );
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