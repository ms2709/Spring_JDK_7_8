package com.munseop.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * Created by 심문섭 on 2016-12-23.
 */
public class ObjectMapperBean extends ObjectMapper {
    public ObjectMapperBean(){
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }
}