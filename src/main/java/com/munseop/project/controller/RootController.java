package com.munseop.project.controller;

import com.munseop.common.ObjectMapperBean;
import com.munseop.project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by temp_administrator on 2016-12-26.
 */
public abstract class RootController {

    @Autowired
    ObjectMapperBean mapperBean;

    //CommonService클래스에 선언된 service name
    @Resource(name="commonService")
    CommonService commonService;

}
