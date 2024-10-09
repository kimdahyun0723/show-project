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
            // 기존 이미지 가져오기
            MemberImg existingImage = memberImgRepository.findByMember(member);
            if (existingImage != null) {
                // 기존 이미지 파일 경로
                String existingImagePath = uploadFolder + existingImage.getUrl().substring("/profileImages/".length());

                // 기존 이미지 파일이 존재하면 삭제
                File existingFile = new File(existingImagePath);
                if (existingFile.exists()) {
                    existingFile.delete(); // 기존 이미지 파일 삭제
                }

                // URL 업데이트
                existingImage.updateUrl("/profileImages/" + imageFileName);
                memberImgRepository.save(existingImage);
            } else {
                // 이미지가 없으면 새로운 MemberImg 객체 생성 후 저장
                MemberImg newImage = MemberImg.builder()
                        .member(member)
                        .url("/profileImages/" + imageFileName)
                        .build();
                memberImgRepository.save(newImage);
            }

            // 새 이미지 파일 저장
            file.transferTo(destinationFile);
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
