package com.teamkrews.chat.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private Long senderId;
    private String content;
}
