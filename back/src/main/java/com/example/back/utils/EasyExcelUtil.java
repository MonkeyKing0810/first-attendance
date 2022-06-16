package com.example.back.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class EasyExcelUtil {



    /**
     * 下载文件
     * @param response
     * @param exportFileName 文件名
     * @param head 实体类
     * @param dataList  数据
     * @throws IOException
     */
    public static void exportDefaultExcel(HttpServletResponse response, String exportFileName, Class head, List<?> dataList) throws IOException {
            // 这里注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(exportFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), head)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("Sheet1").doWrite(dataList);
    }
}