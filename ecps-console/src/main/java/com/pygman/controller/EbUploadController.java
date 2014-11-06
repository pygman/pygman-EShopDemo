package com.pygman.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.pygman.util.UploadResponse;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pygman.util.EbConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Controller
@RequestMapping("/upload")
public class EbUploadController {

	@RequestMapping("/uploadPic.form")
	public void uploadPic(String fileName,HttpServletRequest request,PrintWriter out)throws Exception{
		MultipartHttpServletRequest mr=(MultipartHttpServletRequest) request;
		MultipartFile mf=mr.getFile(fileName);
		byte[] bytes=mf.getBytes();
		String tFileName=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Random random=new Random();
		for(int i=0;i<3;i++){
			tFileName=tFileName+random.nextInt(10);
		}
		String oriFileName=mf.getOriginalFilename();
		String suffix=oriFileName.substring(oriFileName.lastIndexOf("."));
		String realPath=EbConstants.IMAGE_PATH+"/upload/"+tFileName+suffix;
		String relativePath="/upload/"+tFileName+suffix;
		Client client=Client.create();
		WebResource wr=client.resource(realPath);
		wr.put(String.class,bytes);
		JSONObject jo=new JSONObject();
		jo.accumulate("realPath", realPath);
		jo.accumulate("relativePath", relativePath);
		String result=jo.toString();
		out.write(result);
	}
    @RequestMapping("/uploadForFck.form")
    public void uploadForFck(HttpServletRequest request,PrintWriter out)throws Exception{
        MultipartHttpServletRequest mr= (MultipartHttpServletRequest) request;
        Map<String,MultipartFile> map=mr.getFileMap();
        Set<String> keys=map.keySet();
        String key=keys.iterator().next();
        MultipartFile mf=map.get(key);
        byte[] bytes=mf.getBytes();
        String tFileName=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        Random random=new Random();
        for(int i=0;i<3;i++){
            tFileName=tFileName+random.nextInt(10);
        }
        String oriFileName=mf.getOriginalFilename();
        String suffix=oriFileName.substring(oriFileName.lastIndexOf("."));
        String realPath="/upload/"+tFileName+suffix;
        Client client=Client.create();
        WebResource wr=client.resource(realPath);
        wr.put(String.class,bytes);
        UploadResponse ur=new UploadResponse(UploadResponse.EN_OK,realPath);
        out.print(ur);
    }
}
