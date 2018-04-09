package com.epam.test_generator.services;

import static com.epam.test_generator.services.utils.UtilsService.checkNotNull;

import com.epam.test_generator.controllers.admin.JiraSettingsTransformer;
import com.epam.test_generator.controllers.admin.request.JiraSettingsCreateDTO;
import com.epam.test_generator.controllers.admin.request.JiraSettingsUpdateDTO;
import com.epam.test_generator.dao.interfaces.JiraSettingsDAO;
import com.epam.test_generator.entities.JiraSettings;
import com.epam.test_generator.services.exceptions.JiraAuthenticationException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class JiraSettingsService {

    @Autowired
    private JiraSettingsDAO jiraSettingsDAO;

    public JiraSettings createJiraSettings(JiraSettingsCreateDTO jiraSettingsCreateDTO) throws JiraAuthenticationException {
        if (jiraSettingsDAO.findByLogin(jiraSettingsCreateDTO.getLogin()) != null) {
            throw new JiraAuthenticationException(
                    "Jira setting with such login:" + jiraSettingsCreateDTO.getLogin() + " already exist!");
        } else {
            JiraSettings jiraSettings = JiraSettingsTransformer.fromDto(jiraSettingsCreateDTO);
            jiraSettingsDAO.save(jiraSettings);
            return jiraSettings;
        }
    }

    public void updateJiraSettings(Long id, JiraSettingsUpdateDTO jiraSettingsUpdateDTO) {
        JiraSettings jiraSettings = jiraSettingsDAO.findById(id);
        checkNotNull(jiraSettings);
        JiraSettingsTransformer.updateFromDto(jiraSettingsUpdateDTO, jiraSettings);
        jiraSettingsDAO.save(jiraSettings);
    }

    public List<JiraSettings> getJiraSettings() {
        return jiraSettingsDAO.findAll();
    }

}
