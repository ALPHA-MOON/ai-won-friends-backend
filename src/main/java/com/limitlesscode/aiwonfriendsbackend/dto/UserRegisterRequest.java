package com.limitlesscode.aiwonfriendsbackend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

//email, password, name, photo, terms_agree
public record UserRegisterRequest(

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하여야 합니다")
        String password,

        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 2, max = 20, message = "이름은 2글자 이상, 20자 이하여야 합니다")
        String name,

        @URL(protocol = "https", message = "사진의 url이 잘못된 형식입니다")
        String photo,

        @NotNull(message = "반드시 약관에 동의해야 합니다")
        boolean terms_agree
) {
}
