package com.giret.bff.service;

import com.giret.bff.client.DocumentClient;
import com.giret.bff.model.Document;
import com.giret.bff.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentServiceImplTest {

    private DocumentClient documentClient;
    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        documentClient = mock(DocumentClient.class);
        documentService = new DocumentServiceImpl(documentClient);
    }

    @Test
    void testSaveDocument() {

        MultipartFile file = new MockMultipartFile(
                "file", "archivo.pdf", "application/pdf", "Hello World".getBytes()
        );

        Document expected = Document.builder()
                .id(1L)
                .nombreArchivo("archivo.pdf")
                .tipoMime("application/pdf")
                .url("https://url.com/file/archivo.pdf")
                .recursoId(100L)
                .build();

        when(documentClient.saveDocument(100L, file)).thenReturn(expected);


        Document result = documentService.saveDocument(file, 100L);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("archivo.pdf", result.getNombreArchivo());
        verify(documentClient, times(1)).saveDocument(100L, file);
    }

    @Test
    void testFindResourceById() {
        Document doc = Document.builder()
                .id(2L)
                .nombreArchivo("manual.docx")
                .recursoId(5L)
                .build();

        when(documentClient.findResourceById(5L)).thenReturn(List.of(doc));

        List<Document> result = documentService.findResourceById(5L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getRecursoId());
        verify(documentClient, times(1)).findResourceById(5L);
    }

}
