package phone_accessories.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import phone_accessories.app.entity.Category;
import phone_accessories.app.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository; // Mocks the database layer

    @InjectMocks
    private CategoryService categoryService; // Injects the mock into your real service logic

    private Category electronics;

    @BeforeEach
    void setUp() {
        electronics = new Category();
        electronics.setId(1L);
        electronics.setName("Electronics");
        electronics.setDescription("Phone cases, chargers, and cables");
    }

    @Test
    void saveCategory_ShouldReturnSavedCategory() {
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(electronics);

        Category result = categoryService.saveCategory(new Category());

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        assertEquals("Phone cases, chargers, and cables", result.getDescription());
    }

    @Test
    void getById_ShouldReturnCategory_WhenIdExists() {
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(electronics));

        Category result = categoryService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Electronics", result.getName());
    }

    @Test
    void getAllCategories_ShouldReturnCategoryList() {
        Mockito.when(categoryRepository.findAll()).thenReturn(Arrays.asList(electronics));

        List<Category> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getName());
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory_WhenCategoryExists() {
        Category inputDetails = new Category();
        inputDetails.setName("Audio");
        inputDetails.setDescription("Headphones and speakers");

        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName("Audio");
        updatedCategory.setDescription("Headphones and speakers");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(electronics));
        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(1L, inputDetails);

        assertNotNull(result);
        assertEquals("Audio", result.getName());
        assertEquals("Headphones and speakers", result.getDescription());
    }

    @Test
    void deleteCategory_ShouldCallRepositoryDelete() {
        Mockito.doNothing().when(categoryRepository).deleteById(1L);

        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
    }
}