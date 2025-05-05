package org.example.backendenicargy.DTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.backendenicargy.Dto.ReclamationDTO;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;





class ReclamationDTOTest {

    private final Validator validator;

    public ReclamationDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidation_ShouldFailWhenFieldsAreBlank() {
        // Arrange
        ReclamationDTO dto = new ReclamationDTO();
        dto.setTitre("");
        dto.setDescription("");
        dto.setLocal("");
        dto.setSalle("");
        dto.setUserid(null);

        // Act
        var violations = validator.validate(dto);

        // Assert
        assertEquals(5, violations.size());
    }

    @Test
    void testValidation_ShouldPassWhenFieldsAreValid() {
        // Arrange
        ReclamationDTO dto = new ReclamationDTO();
        dto.setTitre("Test");
        dto.setDescription("Test");
        dto.setLocal("Test");
        dto.setSalle("Test");
        dto.setUserid(1L);

        // Act
        var violations = validator.validate(dto);

        // Assert
        assertEquals(0, violations.size());
    }
}
