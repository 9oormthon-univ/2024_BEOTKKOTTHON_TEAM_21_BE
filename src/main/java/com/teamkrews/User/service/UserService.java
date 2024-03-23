package com.teamkrews.User.service;


import com.teamkrews.User.model.User;
import com.teamkrews.User.model.UserInfo;
import com.teamkrews.User.model.UserInfoUpdateDto;
import com.teamkrews.User.model.UserInfos;
import com.teamkrews.User.repository.UserRepository;

import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> {
            log.info("id = {} 인 사용자가 존재하지 않습니다", id);
            return new CustomException(ErrorCode.USER_NOT_FOUND);
        });
    }

    public Boolean isDuplicatedLoginId(String loginId){
        Optional<User> userOptional = userRepository.findByLoginId(loginId);

        if (userOptional.isEmpty()) return Boolean.FALSE;

        return Boolean.TRUE;
    }


    @Transactional
    public User update(UserInfoUpdateDto dto) {
        User user = getById(dto.getUserId());

        if(dto.getNickName() != null)
            user.setNickName(dto.getNickName());
        if(dto.getProfileImageUrl() != null)
            user.setProfileImageUrl(dto.getProfileImageUrl());

        return user;
    }

    public UserInfo convertToInfo(User user){
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user.getNickName());
        userInfo.setProfileImageUrl(user.getProfileImageUrl());
        return userInfo;
    }

    public UserInfos convertToInfos(List<User> userList){
        List<UserInfo> userInfoList = userList.stream().map(
                (user) -> convertToInfo(user)
        ).collect(Collectors.toList());

        return new UserInfos(userInfoList);
    }
}
