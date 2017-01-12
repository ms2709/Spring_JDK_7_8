package com.munseop.project.controller;

import com.munseop.common.model.CurrentMember;
import com.munseop.project.model.Member;
import com.munseop.project.model.Room;
import com.munseop.project.service.CommonService;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017-01-10.
 */
@Controller
public class MemberController {
    @Autowired
    private CommonService commonService;

    @RequestMapping(value="/member/index")
    public String getMemberIndex(HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{
        return "MemberListPage";
    }

    @RequestMapping(value="/member/memberList", produces="application/json; charset=UTF-8", method= RequestMethod.POST)
    public @ResponseBody List<Member> getMemberList(HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception {
        List<Member> members = commonService.getMemberAll();

//        CurrentMember currentMember = (CurrentMember)req.getSession().getAttribute("currentMember");
//        for(Member m:members){
//            if(m.getLoginId().equals(currentMember.getLoginId())) {
//                members.remove(m);
//                break;
//            }
//        }

        return members;
    }
}
