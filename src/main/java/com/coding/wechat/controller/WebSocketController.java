package com.coding.wechat.controller;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/mvc")
public class WebSocketController {

    @GetMapping("/websocket")
    public ModelAndView introduction(Map<String, Object> map) {
        return new ModelAndView("websocket", map);
    }
}
