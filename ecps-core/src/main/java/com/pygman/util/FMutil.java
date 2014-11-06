package com.pygman.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Created by pygmalion on 2014/11/3.
 */
public class FMutil {
    public void ouputFile(String ftlName,String fileName,Map<String,Object> map)throws Exception{
        Configuration config=new Configuration();
        config.setDefaultEncoding("UTF-8");
        config.setClassForTemplateLoading(this.getClass(),"/com/pygman/ftl");
        Template template=config.getTemplate(ftlName);
        String path="D:/ECPS/ecps-portal/web/static";
        Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path+"/"+fileName)),"UTF-8"));
        template.process(map,writer);
    }
}
