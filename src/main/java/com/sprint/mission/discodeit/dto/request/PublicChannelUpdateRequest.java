package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicChannelUpdateRequest(
        @NotBlank(message = "채팅방 이름은 필수입니다.")
        @Size(max = 30, message = "채팅방 이름은 최대 30글자입니다.")
        String newName,

        @Size(max = 200, message = "채널 설명은 200자 이하여야 합니다.")
        String newDescription
) {

}
