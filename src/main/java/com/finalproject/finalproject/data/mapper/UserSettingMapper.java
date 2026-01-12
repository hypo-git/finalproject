package com.finalproject.finalproject.data.mapper;

import com.finalproject.finalproject.data.dto.UserSettingDTO;
import com.finalproject.finalproject.data.model.UserSetting;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserSettingMapper {

    UserSetting toUserSetting(UserSettingDTO userSettingDTO);
    UserSettingDTO toUserSettingDTO(UserSetting userSetting);

    void updateEntityFromDTO(UserSettingDTO userSettingDTO, @MappingTarget UserSetting userSetting);
}
