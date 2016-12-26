package com.munseop.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.munseop.common.ObjectMapperBean;
import com.munseop.project.model.Member;
import com.munseop.project.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String movePage(HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws JsonProcessingException {
        List<Integer> items = commonService.getTestService();
        Member member = commonService.getMemberTest1();
        Member member1 = commonService.getMemberTest2();

        return "TestPage";

    }


    @RequestMapping("/test/getMember")
    public @ResponseBody Member getMember(){
        return commonService.getMemberTest1();
    }

}
