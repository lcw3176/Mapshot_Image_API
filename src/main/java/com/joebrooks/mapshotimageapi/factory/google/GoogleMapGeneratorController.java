package com.joebrooks.mapshotimageapi.factory.google;

import com.joebrooks.mapshotimageapi.global.model.UserMapRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/gen/google")
public class GoogleMapGeneratorController {

    @GetMapping
    public String getKakaoMap(@ModelAttribute UserMapRequest mapRequest, Model model){
        model.addAttribute("mapRequest", mapRequest);

        return "map/google";
    }

}
