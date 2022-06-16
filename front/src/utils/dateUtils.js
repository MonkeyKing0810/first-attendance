/**
 * 计算两个时间的差值
 */
export function getTime(beginTime, endTime) {
    console.log("进入了自定义getTime")
    var dateDiff = endTime.getTime() - beginTime.getTime();//时间差的毫秒数
    console.log("自定义getTime的开始时间：", endTime.getTime())
    console.log("自定义getTime的结束时间：", beginTime.getTime())
    console.log("自定义getTime的dateDiff", dateDiff)
    var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数
    var leave1 = dateDiff % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000))//计算出小时数
    //计算相差分钟数
    var leave2 = leave1 % (3600 * 1000)    //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000))//计算相差分钟数
    //计算相差秒数
    var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000)
    console.log(" 相差 " + dayDiff + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒")
    // return "相差 " + dayDiff + "天 " + hours + "小时 " + minutes + " 分钟";
    //这里要天数
    return "共 " + dayDiff + "天 ";
}
