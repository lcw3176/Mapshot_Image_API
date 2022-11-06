package com.joebrooks.mapshotimageapi.task.map;

import com.joebrooks.mapshotimageapi.task.TaskRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/gen")
public class MapGeneratorController {

    @GetMapping("/kakao")
    public String getKakaoMap(@ModelAttribute TaskRequest mapRequest, Model model){
        model.addAttribute("mapRequest", mapRequest);

        return "map/kakao";
    }

    @GetMapping("/google")
    public String getGoogleMap(@ModelAttribute TaskRequest mapRequest, Model model){
        model.addAttribute("mapRequest", mapRequest);

        return "map/google";
    }

}
