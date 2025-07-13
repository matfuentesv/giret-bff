package com.giret.bff.controller;

import com.giret.bff.model.Document;
import com.giret.bff.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentControllerTest {

    private DocumentService documentService;
    private DocumentController documentController;

    @BeforeEach
    void setUp() {
        documentService = mock(DocumentService.class);
        documentController = new DocumentController(documentService);
    }

    @Test
    void testUpload() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "Hello World".getBytes()
        );

        Document savedDoc = Document.builder()
                .id(1L)
                .nombreArchivo("test.pdf")
                .tipoMime("application/pdf")
                .url("https://url.com/file/test.pdf")
                .recursoId(10L)
                .build();

        when(documentService.saveDocument(file, 10L)).thenReturn(savedDoc);

        // Act
        ResponseEntity<Document> response = documentController.upload(file, 10L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test.pdf", response.getBody().getNombreArchivo());
        assertEquals(10L, response.getBody().getRecursoId());
        verify(documentService, times(1)).saveDocument(file, 10L);
    }

    @Test
    void testFindByRecursoId() {
        Document doc = Document.builder()
                .id(2L)
                .nombreArchivo("manual.docx")
                .recursoId(5L)
                .build();

        when(documentService.findResourceById(5L)).thenReturn(List.of(doc));

        ResponseEntity<List<Document>> response = documentController.findByRecursoId(5L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(5L, response.getBody().get(0).getRecursoId());
        verify(documentService, times(1)).findResourceById(5L);
    }
}
