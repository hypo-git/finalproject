package com.finalproject.finalproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingDTO {
    private String colorScheme;
    private String fontSize;
    private String primaryColor;
}
