package com.multi.happytails.help.controller;

import com.multi.happytails.help.model.dto.InquiryDto;
import com.multi.happytails.help.model.dto.InquiryResultDto;
import com.multi.happytails.help.service.HelpService;
import com.multi.happytails.upload.model.dto.UploadDto;
import com.multi.happytails.upload.service.UploadService;
import jakarta.annotation.Nullable;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * packageName    : com.multi.happytails.help.controller
 * fileName       : HelpAdminController
 * author         : NamDongSeok
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        NamDongSeok       최초 생성
 */
@Controller
@RequestMapping("/admin")
public class HelpAdminController {
    @Autowired
    HelpService helpService;

    @Autowired
    UploadService uploadService;

    final String UPLOAD_ADMIN_INQUIRY_CODE = "IA";

    @PostMapping("/inquiry/resultWrite")
    @ResponseBody
    public String inquiryWrite(@ModelAttribute InquiryResultDto inquiryResultDto
            , @RequestParam(value = "imageFiles") @Nullable List<MultipartFile> imageFiles) {
        System.out.println(inquiryResultDto);

        long inquiryResultNo = helpService.inquiryResultWrite(inquiryResultDto);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            UploadDto uploadDto = new UploadDto();
            uploadDto.setForeignNo(inquiryResultNo);
            uploadDto.setCategoryCode(UPLOAD_ADMIN_INQUIRY_CODE);

            for (int i = 0; i < imageFiles.size(); i++) {
                uploadDto.setFile(imageFiles.get(i));
                uploadService.uploadInsert(uploadDto);
            }
        }

        helpService.inquiryResultChange(InquiryDto.builder().inquiryNo(inquiryResultDto.getInquiryNo()).result('Y').build());

        return "문의 작성이 완료 되었습니다.";
    }
}
