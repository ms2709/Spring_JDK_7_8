package com.munseop.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.munseop.common.ObjectMapperBean;
import com.munseop.common.model.CurrentMember;
import com.munseop.project.model.Member;
import com.munseop.project.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 심문섭 on 2016-12-23.
 */

@Controller
public class CommonController extends RootController {
    private final Logger logger = LoggerFactory.getLogger( CommonController.class );

    @RequestMapping("/movePage")
    public String movePage(ModelMap model, HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws JsonProcessingException, Exception {
//        List<Integer> items = commonService.getTestService();
//        Member member = commonService.getMemberTest1();
//        Member member1 = commonService.getMemberTest2();

        CurrentMember currentMember = CommonController.IsLoginUser(req);

        if(currentMember == null || currentMember.isLoginMember() == false){
            model.addAttribute("loginUser", currentMember == null ? new CurrentMember() : currentMember);
            model.addAttribute("loginResult", req.getSession().getAttribute("loginMessage"));
            sessionStatus.setComplete();
            return "LoginPage";
        }
        else{
            req.getSession().removeAttribute("loginMessage");
            model.addAttribute("loginUser", currentMember);
            model.addAttribute("loginResult", "");
            return new MemberController().getMemberIndex(req, res, sessionStatus);
        }

        //return "StompPage";
    }

    public static CurrentMember IsLoginUser(HttpServletRequest req) {
        CurrentMember currentMember= (CurrentMember)req.getSession().getAttribute("currentMember");

        return currentMember;
    }

    @RequestMapping("/test/getMember")
    public @ResponseBody Member getMember(){
        return null;
    }

    @RequestMapping(value = "/loginMember")
    public String checkMember(@ModelAttribute("loginUser")CurrentMember loginMember, HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus)
        throws  Exception{
        Member member = commonService.getMemberByUserIdNPassword(loginMember.getLoginId(), loginMember.getPassword());

        if(member != null){
            CurrentMember currentMember = new CurrentMember();
            currentMember.setLoginId(loginMember.getLoginId());
            currentMember.setLoginMember(true);
            currentMember.setUserName(loginMember.getUserName());
            req.getSession().setAttribute("currentMember", currentMember);

            if(loginMember.getLoginId().equals("admin")) {
                return new MemberController().getMemberIndex(req, res, sessionStatus);
            }
            else{
                return new RoomController().getRoomListIndex(req, res, sessionStatus);
            }
        }
        else{
            return "redirect:/";
        }
    }
}
