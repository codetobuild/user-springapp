package com.nagarro.userapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.userapp.model.Gender;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GenderMapperTest {

    @Mock
    private ObjectMapper objectMapper;

    @Test
    void mapToGender_ValidResponse_SuccessfullyMapped() throws Exception {
        // Arrange
        String validResponse = "{ \"gender\": \"male\" }";
        Gender expectedGender = Gender.builder().gender("male").build();

        // Mocking ObjectMapper behavior
        when(objectMapper.readTree(validResponse)).thenReturn(mock(JsonNode.class));

        // Act
        Gender actualGender = GenderUtil.mapToGender(validResponse);

        // Assert
        assertEquals(expectedGender, actualGender);
    }


    @Test
    public void testMapToGender() throws IOException, JsonProcessingException {
        // Mock the ObjectMapper
        ObjectMapper objectMapperMock = mock(ObjectMapper.class);
        JsonNode rootMock = mock(JsonNode.class);
        String validResponse = "{ \"gender\": \"Male\" }";

        // Set up the mock behavior
        when(objectMapperMock.readTree(validResponse)).thenReturn(rootMock);
        when(rootMock.get("gender")).thenReturn(mock(JsonNode.class));
        when(mock(JsonNode.class).textValue()).thenReturn("Male");

        // Create the GenderMapper instance

        // Call the mapToGender method
        Gender gender = GenderUtil.mapToGender(validResponse);

        // Verify the results
        assertEquals(Gender.builder().gender("Male").build(), gender);
    }
}
