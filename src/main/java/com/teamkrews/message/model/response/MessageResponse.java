package com.teamkrews.message.model.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {

    private Long senderId;

    private String content;

    private LocalDateTime createdAt;
}
