package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoObjectsInStorage_ReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        List<SearchResult> results = searchService.search("test");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenNoMatches_ReturnsEmptyList() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("unrelated");
        when(storageService.getAllSearchables()).thenReturn(List.of(mockSearchable));

        List<SearchResult> results = searchService.search("test");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchExists_ReturnsResult() {
        Searchable mockSearchable = mock(Searchable.class);
        when(mockSearchable.getSearchTerm()).thenReturn("test product");
        when(mockSearchable.getId()).thenReturn(UUID.randomUUID());
        when(mockSearchable.getName()).thenReturn("Test Product");
        when(mockSearchable.getContentType()).thenReturn("PRODUCT");
        when(storageService.getAllSearchables()).thenReturn(List.of(mockSearchable));

        List<SearchResult> results = searchService.search("test");

        assertEquals(1, results.size());
        SearchResult result = results.get(0);
        assertEquals("Test Product", result.getName());
        assertEquals("PRODUCT", result.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenPatternIsBlank_ReturnsEmptyList() {
        List<SearchResult> results = searchService.search(" ");

        assertTrue(results.isEmpty());
        verifyNoInteractions(storageService);
    }

    @Test
    void search_WhenPatternIsNull_ReturnsEmptyList() {
        List<SearchResult> results = searchService.search(null);

        assertTrue(results.isEmpty());
        verifyNoInteractions(storageService);
    }
}