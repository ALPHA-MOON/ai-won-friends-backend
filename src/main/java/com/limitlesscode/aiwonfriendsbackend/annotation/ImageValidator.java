package com.limitlesscode.aiwonfriendsbackend.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

    private final List<String> allowedContentTypes =
            List.of("image/jpeg", "image/png");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        // 파일 필수 업로드 아닐 경우 허용
        if (file == null || file.isEmpty()) {
            return true;
        }

        //content-types가 안맞으면 안됨
        String contentType = file.getContentType();
        return contentType != null && allowedContentTypes.contains(contentType);
    }
}
