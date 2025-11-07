package com.limitlesscode.aiwonfriendsbackend.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private int maxFileSizeBytes;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxFileSizeBytes = constraintAnnotation.maxSizeKb() * 1024;
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

        if (file == null || file.isEmpty()) {
            return true;
        }

        return file.getSize() <= maxFileSizeBytes;

    }
}
