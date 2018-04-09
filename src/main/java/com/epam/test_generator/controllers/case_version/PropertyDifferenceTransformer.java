package com.epam.test_generator.controllers.case_version;

import com.epam.test_generator.controllers.case_version.response.PropertyDifferenceDTO;
import com.epam.test_generator.pojo.PropertyDifference;

public class PropertyDifferenceTransformer {
    public static PropertyDifferenceDTO toDTO(PropertyDifference prop) {
        return new PropertyDifferenceDTO(
                prop.getPropertyName(),
                prop.getOldValue(),
                prop.getNewValue()
        );
    }
}
