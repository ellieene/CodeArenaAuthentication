package com.example.CodeArena.service.user.impl;

import com.example.CodeArena.exception.EntityNotFoundException;
import com.example.CodeArena.model.dto.UserDTO;
import com.example.CodeArena.model.entity.User;
import com.example.CodeArena.model.responce.StringResponse;
import com.example.CodeArena.repository.UserRepository;
import com.example.CodeArena.service.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.CodeArena.util.CommonStrings.UNCORRECT_EMAIL_AND_LOGIN;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUser(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN));
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        return userDTO;
    }

//    @Transactional
//    @Override
//    public StringResponse editUser(UUID userId){
//
//        return new StringResponse("f");
//    }
//


}
