package com.pygman.controller;

import com.pygman.model.EbShipAddr;
import com.pygman.model.TsPtlUser;
import com.pygman.service.EbShipAddrService;
import com.pygman.service.TsPtlUserService;
import com.pygman.util.MD5;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by pygmalion on 2014/11/4.
 */
@Controller
@RequestMapping("/user")
public class EbUserController {
    @Autowired
    TsPtlUserService userService;
    @Autowired
    EbShipAddrService addrService;
    @RequestMapping("/toLogin.form")
    public String toLogin(){
        return "passport/login";
    }
    @RequestMapping("/getImage.form")
    public void getImage(HttpServletRequest request,HttpServletResponse response)throws Exception{
        System.out.println("#######################生成数字和字母的验证码#######################");
        BufferedImage img=new BufferedImage(68,22,BufferedImage.TYPE_INT_BGR);
        Graphics g=img.getGraphics();
        Random r=new Random();
        Color c=new Color(200,150,255);
        g.setColor(c);
        g.fillRect(0,0,68,22);
        StringBuffer stringBuffer=new StringBuffer();
        char[] chars="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int index,len= chars.length;
        for (int i=0;i<4;i++){
            index=r.nextInt(len);
            g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));
            g.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,22));
            g.drawString(""+chars[index],(i*15)+3,18);
            stringBuffer.append(chars[index]);
            request.getSession().setAttribute("piccode",stringBuffer.toString());
            ImageIO.write(img, "JPG",response.getOutputStream());
        }
    }
    @RequestMapping("/login.form")
    public String login(String username,String password,String captcha,HttpSession session,Model model){
        String rightCap= (String) session.getAttribute("piccode");
        if(!StringUtils.equalsIgnoreCase(captcha, rightCap)){
            model.addAttribute("tip","cap_error");
            return "passport/login";
        }
        String newPass=new MD5().GetMD5Code(password);
        Map<String,String> map=new HashMap<String, String>();
        map.put("username",username);
        map.put("password",password);
        TsPtlUser user=userService.selectUserByUserPass(map);
        if(user==null){
            model.addAttribute("tip","user_error");
            return "passport/login";
        }
        session.setAttribute("user",user);
        return "redirect:/item/toIndex.form";
    }
    @RequestMapping("/ajaxLogin.form")
    public void ajaxLogin(String username,String password,String captcha,HttpSession session,PrintWriter out){
        String rightCap= (String) session.getAttribute("piccode");
        if(!StringUtils.equalsIgnoreCase(captcha, rightCap)){
            out.write("cap_error");
            return;
        }
        String newPass=new MD5().GetMD5Code(password);
        Map<String,String> map=new HashMap<String, String>();
        map.put("username",username);
        map.put("password",password);
        TsPtlUser user=userService.selectUserByUserPass(map);
        if(user==null){
            out.write("user_error");
            return;
        }
        session.setAttribute("user",user);
        out.write("success");
    }
    @RequestMapping("/getUser.form")
    public void getUser(HttpSession session,HttpServletResponse response)throws Exception{
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        response.setContentType("text/html;charset=UTF-8");
        JSONObject jo=new JSONObject();
        jo.accumulate("user",user);
        String result=jo.toString();
        response.getWriter().write(result);
    }
    @RequestMapping("/toPersonIndex.form")
    public String toPersonIndex(HttpSession session){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        return "person/index";
    }
    @RequestMapping("/toProfile.form")
    public String toProfile(HttpSession session,Model model){
        TsPtlUser user=(TsPtlUser)session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        model.addAttribute("user",user);
        return "person/profile";
    }
    @RequestMapping("/updateUser.form")
    public void updateUser(HttpSession session,TsPtlUser user1,PrintWriter out){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        String tip="success";
        if(user==null){
            tip="noLogin";
        }else{
            userService.updateUser(user1);
            TsPtlUser newUser=userService.selectUserById(user.getPtlUserId());
            session.setAttribute("user",newUser);
        }
        out.write(tip);
    }
    @RequestMapping("/toAddr.form")
    public String toAddr(HttpSession session,Model model){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        List<EbShipAddr> addrList=addrService.selectAddrByUserId(user.getPtlUserId());
        model.addAttribute("addrList",addrList);
        return "person/deliverAddress";
    }
    @RequestMapping("/getAddrById.form")
    public void getAddrById(HttpSession session,Long shipAddrId,HttpServletResponse response)throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        String tip="success";
        JSONObject jo=new JSONObject();
        if(user==null){
            tip="noLogin";
        }else{
            EbShipAddr addr=addrService.selectAddrById(shipAddrId);
            jo.accumulate("addr",addr);
        }
        jo.accumulate("tip",tip);
        String result=jo.toString();
        response.getWriter().write(result);
    }
    @RequestMapping("/updateOrSaveAddr.form")
    public String updateOrSaveAddr(EbShipAddr addr,HttpSession session){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        if(addr.getDefaultAddr()==null){
            addr.setDefaultAddr((short)0);
        }
        addr.setPtlUserId(user.getPtlUserId());
        addrService.updateOrSaveAddr(addr);
        return "redirect:toAddr.form";
    }
    @RequestMapping("/setDefaultAddr.form")
    public String setDefaultAddr(Long shipAddrId,HttpSession session){
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        if(user==null){
            return "redirect:toLogin.form";
        }
        addrService.updateDefaultAddr(shipAddrId,user.getPtlUserId());
        return "redirect:toAdr.form";
    }
    @RequestMapping("/validDefaultAddr.form")
    public void validDefaultAddr(HttpSession session,PrintWriter out)throws Exception{
        TsPtlUser user= (TsPtlUser) session.getAttribute("user");
        String tip="success";
        JSONObject jo=new JSONObject();
        if(user==null){
            tip="noLogin";
        }else{
            EbShipAddr addr=addrService.selectDefaultAddr(user.getPtlUserId());
            if(addr==null){
                jo.accumulate("YesOrNo","yes");
            }else {
                jo.accumulate("YesOrNo","no");
            }
            jo.accumulate("tip",tip);
            String result=jo.toString();
            out.write(result);
        }
    }
}
