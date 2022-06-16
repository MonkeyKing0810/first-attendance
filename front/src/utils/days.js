/**
 * 计算两个时间的差值
 */
export function days(beginTime, endTime) {
    console.log("求天数")

    var dateDiff = endTime.getTime() - beginTime.getTime();//时间差的毫秒数

    var dayDiff = Math.floor(dateDiff / (24 * 3600 * 1000));//计算出相差天数

    return dayDiff;
}
