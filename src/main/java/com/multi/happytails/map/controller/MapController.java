package com.multi.happytails.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/map")
@Controller
public class MapController {

    @RequestMapping("/map")
    public String map(){

        return "redirect:/map/mapview";
    }

    @RequestMapping("/mapview")
    public String mapView(){

        return "/map/map";
    }

}
