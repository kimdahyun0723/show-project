package com.keduit.show.service;

import com.keduit.show.dto.ImageResponseDTO;
import com.keduit.show.dto.ImageUploadDTO;
import com.keduit.show.entity.Member;
import com.keduit.show.entity.MemberImg;
import com.keduit.show.repository.MemberImgRepository;
import com.keduit.show.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberImgService {

    private final MemberImgRepository memberImgRepository;
    private final MemberRepository memberRepository;

    @Value("${file.profileImagePath}")
    private String uploadFolder;

    public void upload(ImageUploadDTO imageUploadDTO, String id) {
        Member member = memberRepository.findById(id);
        MultipartFile file = imageUploadDTO.getFile();

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + file.getOriginalFilename();

        File destinationFile = new File(uploadFolder + imageFileName);

        try {
            file.transferTo(destinationFile);

            MemberImg image = memberImgRepository.findByMember(member);
            if (image != null) {
                // 이미지가 이미 존재하면 url 업데이트
                image.updateUrl("/images/" + imageFileName);
            } else {
                // 이미지가 없으면 객체 생성 후 저장
                image = MemberImg.builder()
                        .member(member)
                        .url("/images/" + imageFileName)
                        .build();
            }
            memberImgRepository.save(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageResponseDTO findImage(String id) {
        Member member = memberRepository.findById(id);
        MemberImg image = memberImgRepository.findByMember(member);

        String defaultImageUrl = "/images/defaultProfile.png";

        if (image == null) {
            return ImageResponseDTO.builder()
                    .url(defaultImageUrl)
                    .build();
        } else {
            return ImageResponseDTO.builder()
                    .url(image.getUrl())
                    .build();
        }
    }

}
