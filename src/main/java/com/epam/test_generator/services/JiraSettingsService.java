package com.epam.test_generator.services;

import static com.epam.test_generator.services.utils.UtilsService.checkNotNull;

import com.epam.test_generator.controllers.Admin.JiraSettingsTransformer;
import com.epam.test_generator.controllers.Admin.request.JiraSettingsCreateDTO;
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
            JiraSettings jiraSettings = JiraSettingsTransformer.fromDTO(jiraSettingsCreateDTO);
            jiraSettingsDAO.save(jiraSettings);
            return jiraSettings;
        }
    }

    public void updateJiraSettings(Long id, JiraSettingsCreateDTO jiraSettingsCreateDTO) {
        JiraSettings jiraSettings = jiraSettingsDAO.findById(id);
        checkNotNull(jiraSettings);
        jiraSettings.setLogin(jiraSettingsCreateDTO.getLogin());
        jiraSettings.setPassword(jiraSettingsCreateDTO.getPassword());
        jiraSettings.setUri(jiraSettingsCreateDTO.getUri());
        jiraSettingsDAO.save(jiraSettings);
    }

    public List<JiraSettings> getJiraSettings() {
        return jiraSettingsDAO.findAll();
    }

}
