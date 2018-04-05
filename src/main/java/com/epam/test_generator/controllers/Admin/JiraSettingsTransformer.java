package com.epam.test_generator.controllers.Admin;

import com.epam.test_generator.controllers.Admin.request.JiraSettingsCreateDTO;
import com.epam.test_generator.controllers.Admin.response.JiraSettingsGetDTO;
import com.epam.test_generator.entities.JiraSettings;

public class JiraSettingsTransformer {

    public static JiraSettingsGetDTO toDTO(JiraSettings settings) {
        return new JiraSettingsGetDTO(
                settings.getId(),
                settings.getUri(),
                settings.getLogin()
        );
    }

    public static JiraSettings fromDTO(JiraSettingsCreateDTO dto) {
        return new JiraSettings(
                dto.getUri(),
                dto.getLogin(),
                dto.getPassword()
        );
    }

}
