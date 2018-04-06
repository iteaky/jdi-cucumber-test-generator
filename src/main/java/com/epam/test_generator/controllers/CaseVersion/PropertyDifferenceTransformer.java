package com.epam.test_generator.controllers.CaseVersion;

import com.epam.test_generator.controllers.CaseVersion.response.PropertyDifferenceGetDTO;
import com.epam.test_generator.pojo.PropertyDifference;

public class PropertyDifferenceTransformer {
    public static PropertyDifferenceGetDTO toDTO(PropertyDifference prop) {
        return new PropertyDifferenceGetDTO(
                prop.getPropertyName(),
                prop.getOldValue(),
                prop.getNewValue()
        );
    }
}
