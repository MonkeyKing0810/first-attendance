package com.example.back.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.example.back.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/file")
public class FileController {
	@Value("${file.uploadFolder}")
	private String uploadFolder;

	@RequestMapping("/upload")
	public String FileUpdate (HttpServletRequest request,MultipartFile file) {
		//配置文件设置想保存的路径
		String str=uploadFolder;
		//得到上传时的文件名字
		 String originalname=file.getOriginalFilename();
		System.out.println("上传时的文件名字:"+originalname);

		 //substring(originalname.lastIndexOf(".")截取图片名后缀
		 String newName= originalname.substring(originalname.lastIndexOf("."));
		System.out.println("图片名字："+originalname);
		System.out.println("截取图片名后缀:"+newName);

		String frontNewName= originalname.substring(0,originalname.lastIndexOf("."));
		System.out.println("图片名字："+originalname);
		System.out.println("截取图片名前缀:"+frontNewName);

		 //利用UUidUtil工具类生成新的文件名字
		 newName = UuidUtil.getUUID()+newName;
		 
		   System.out.println(newName);
		   System.out.println(str);
		   File newFile=new File(str,newName);
		   
		   //获得文件目录，判断是否存在
		   if(!newFile.getParentFile().exists()) {
			   //如果path路径不存在，创建一个文件夹，存在则使用当前文件夹
			   newFile.getParentFile().mkdirs();
		   }
		   try {
			   //保存文件到指定路径之中
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
		
			e.printStackTrace();
		}
		   System.out.println("上传的文件路径:"+str+newName);
		   return newName;


	}
}