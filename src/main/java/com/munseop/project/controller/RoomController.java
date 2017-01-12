package com.munseop.project.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.munseop.project.model.Room;
import com.munseop.project.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * Created by Administrator on 2017-01-10.
 */
@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping(value="/room/createRoom")
    public String createRoom(MultipartHttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{
        String jsonRoom = req.getParameter("room");
        Room room = new ObjectMapper().readValue(jsonRoom, Room.class);
        roomService.createRoom(room);

        return indexRoom(room, req, res, sessionStatus);
    }

    @RequestMapping(value="/room/selectRoom")
    public String selectRoom(MultipartHttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{
        String jsonRoom = req.getParameter("room");
        Room room = new ObjectMapper().readValue(jsonRoom, Room.class);

        return indexRoom(room, req, res, sessionStatus);
    }

    @RequestMapping(value="/room/index")
    public String indexRoom(@ModelAttribute("room")Room room, HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{

        String jsonRoom = new ObjectMapper().writeValueAsString(room);
        res.addCookie(new Cookie("room", URLEncoder.encode(jsonRoom, "UTF-8")));
        return "RoomPage";
    }

    @RequestMapping(value="/room/list")
    public String getRoomListIndex(HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{
        return "RoomListPage";
    }

    @RequestMapping(value="/room/roomList", produces="application/json; charset=UTF-8", method= RequestMethod.POST)
    public @ResponseBody
    List<Room> getRoomList(HttpServletRequest req, HttpServletResponse res, SessionStatus sessionStatus) throws Exception{
        return roomService.getRoomList();
    }
//    public static String getBody(HttpServletRequest request) throws IOException {
//
//        String body = null;
//        StringBuilder stringBuilder = new StringBuilder();
//        BufferedReader bufferedReader = null;
//
//        try {
//            InputStream inputStream = request.getInputStream();
//            if (inputStream != null) {
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                char[] charBuffer = new char[128];
//                int bytesRead = -1;
//                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//                    stringBuilder.append(charBuffer, 0, bytesRead);
//                }
//            }
//        } catch (IOException ex) {
//            throw ex;
//        } finally {
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException ex) {
//                    throw ex;
//                }
//            }
//        }
//
//        body = stringBuilder.toString();
//        return body;
//    }
}
