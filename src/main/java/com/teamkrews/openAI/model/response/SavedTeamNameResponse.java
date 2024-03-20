package com.teamkrews.openAI.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedTeamNameResponse {

    private String workspaceUUID;

    private String teamName;

    private String explanation;
}
