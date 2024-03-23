package com.teamkrews.message.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageResponses {
    List<MessageResponse> messageResponseList;

    public MessageResponses(List<MessageResponse> messageResponseList) {
        this.messageResponseList = messageResponseList;
    }
}
