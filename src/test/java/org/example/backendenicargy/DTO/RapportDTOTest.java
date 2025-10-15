package org.example.backendenicargy.DTO;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.backendenicargy.Dto.RapportDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapportDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRapportDTO() {
        // Arrange
        RapportDTO dto = new RapportDTO(1L, "Valid Title", "reports/valid.pdf", "2023-01-01");

        // Act
        var violations = validator.validate(dto);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRapportDTO_BlankTitle() {
        // Arrange
        RapportDTO dto = new RapportDTO();
        dto.setTitle(""); // Blank title

        // Act
        var violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }
}
