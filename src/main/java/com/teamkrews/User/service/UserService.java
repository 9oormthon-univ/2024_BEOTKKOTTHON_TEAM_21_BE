package com.teamkrews.User.service;


import com.teamkrews.User.model.User;
import com.teamkrews.User.model.UserInfoUpdateDto;
import com.teamkrews.User.model.response.UserInfoResponse;
import com.teamkrews.User.repository.UserRepository;
import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.chat.repository.ChatRoomRepository;

import java.util.List;

import com.teamkrews.global.exception.CustomException;
import com.teamkrews.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    public User update(UserInfoUpdateDto dto) {
        User user = getById(dto.getUserId());
        user.setNickName(dto.getNickName());
        return user;
    }
}
