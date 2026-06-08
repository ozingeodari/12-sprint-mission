package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(min = 2, max = 20, message = "사용자 이름은 2글자 이상, 20글자 이하여야 합니다.")
        String newUsername,

        @Email(message = "이메일 형식을 만족해야 합니다.")
        @Size(max = 100, message = "이메일은 100글자 이하여야 합니다.")
        String newEmail,

        @Size(min = 8, max = 20, message = "비밀번호는 8글자 이상, 20글자 이하여야 합니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,}$",
                message = "비밀번호는 숫자, 문자, 특수문자를 포함해야 합니다.")
        String newPassword
) {

}
