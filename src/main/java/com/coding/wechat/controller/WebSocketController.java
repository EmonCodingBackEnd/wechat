package com.coding.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/mvc")
public class WebSocketController {

    @GetMapping("/websocket/{userId}")
    public ModelAndView introduction(
            @PathVariable("userId") String userId, Map<String, Object> map) {
        map.put("userId", userId);
        return new ModelAndView("websocket", map);
    }
}
