package com.finalproject.finalproject.service;

import com.finalproject.finalproject.data.dto.UserSettingDTO;
import com.finalproject.finalproject.data.mapper.UserSettingMapper;
import com.finalproject.finalproject.data.model.User;
import com.finalproject.finalproject.data.model.UserSetting;
import com.finalproject.finalproject.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSettingService {
    private final UserSettingRepository userSettingRepository;
    private final UserSettingMapper userSettingMapper;

    public UserSetting getOrCreateUserSetting(User user){
        if (user.getSettings() == null) {
            UserSetting userSetting = UserSetting.builder()
                    .user(user)
                    .colorScheme("light")
                    .fontSize("md")
                    .primaryColor("blue")
                    .build();
            user.setSettings(userSetting);
            userSettingRepository.save(userSetting);
        }
        return user.getSettings();
    }

    @Transactional
    public UserSettingDTO updateUserSettingDTO(User user, UserSettingDTO userSettingDTO){
        UserSetting userSetting = getOrCreateUserSetting(user);

        userSettingMapper.updateEntityFromDTO(userSettingDTO, userSetting);

        userSettingRepository.save(userSetting);

        return userSettingMapper.toUserSettingDTO(userSetting);
    }

}
