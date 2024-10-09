package com.keduit.show.controller;

import com.keduit.show.dto.ImageUploadDTO;
import com.keduit.show.service.MemberImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
public class MemberImgController {

    private final MemberImgService memberImgService;

    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDTO imageUploadDTO, Principal principal) {
        memberImgService.upload(imageUploadDTO, principal.getName());

        return "redirect:/members/info";
    }

}
