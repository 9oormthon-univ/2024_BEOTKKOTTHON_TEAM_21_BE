package com.teamkrews.openAI.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
public class CompletionRequest {

    private String model;

    private String prompt;

    private float temperature;
}
