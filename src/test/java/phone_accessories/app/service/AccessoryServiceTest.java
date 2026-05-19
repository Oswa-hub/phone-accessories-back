package phone_accessories.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import phone_accessories.app.entity.Accessory;
import phone_accessories.app.entity.Brand;
import phone_accessories.app.entity.Category;
import phone_accessories.app.repository.AccessoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccessoryServiceTest {

    @Mock
    private AccessoryRepository accessoryRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BrandService brandService;

    @InjectMocks
    private AccessoryService accessoryService; // System under test

    private Accessory caseAccessory;
    private Category electronics;
    private Brand apple;

    @BeforeEach
    void setUp() {
        electronics = new Category();
        electronics.setId(1L);
        electronics.setName("Electronics");

        apple = Brand.builder().id(2L).name("Apple").build();

        caseAccessory = Accessory.builder()
                .id(5L)
                .name("Silicone Case")
                .price(29.99)
                .stock(50)
                .category(electronics)
                .brand(apple)
                .build();
    }

    @Test
    void createAccessory_ShouldSaveAndReturnAccessory() {
        Mockito.when(accessoryRepository.save(any(Accessory.class))).thenReturn(caseAccessory);

        Accessory result = accessoryService.createAccessory(new Accessory());

        assertNotNull(result);
        assertEquals("Silicone Case", result.getName());
        assertEquals(29.99, result.getPrice());
    }

    @Test
    void getById_ShouldReturnAccessory_WhenIdExists() {
        Mockito.when(accessoryRepository.findById(5L)).thenReturn(Optional.of(caseAccessory));

        Accessory result = accessoryService.getById(5L);

        assertNotNull(result);
        assertEquals(5L, result.getId());
    }

    @Test
    public void getAllAccessories_ShouldReturnList() {
        Mockito.when(accessoryRepository.findAll()).thenReturn(Arrays.asList(new Accessory()));

        // 🎯 Pass null, null to hit the unfiltered database fetch block!
        List<Accessory> result = accessoryService.filterAccessories(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void update_ShouldModifyAndSaveAccessoryWithNewBrandAndCategory() {
        // Arrange
        Accessory incomingDetails = Accessory.builder()
                .name("Leather Case")
                .price(49.99)
                .stock(20)
                .build();

        Category originalCategory = new Category();
        Brand originalBrand = new Brand();

        Accessory mockExistingInDb = Accessory.builder()
                .id(5L)
                .name("Old Name")
                .category(originalCategory)
                .brand(originalBrand)
                .build();

        Mockito.when(accessoryRepository.findById(5L)).thenReturn(Optional.of(mockExistingInDb));
        Mockito.when(categoryService.getById(1L)).thenReturn(electronics);
        Mockito.when(brandService.getById(2L)).thenReturn(apple);

        // Return whatever is passed to save so we can inspect it
        Mockito.when(accessoryRepository.save(any(Accessory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Accessory result = accessoryService.update(5L, incomingDetails, 1L, 2L);

        // Assert
        assertNotNull(result);
        assertEquals("Leather Case", result.getName());
        assertEquals(49.99, result.getPrice());
        assertEquals(20, result.getStock());
        assertEquals("Electronics", result.getCategory().getName());
        assertEquals("Apple", result.getBrand().getName());
    }

    @Test
    void delete_ShouldInvokeRepositoryDelete() {
        Mockito.when(accessoryRepository.findById(5L)).thenReturn(Optional.of(caseAccessory));
        Mockito.doNothing().when(accessoryRepository).delete(caseAccessory);

        assertDoesNotThrow(() -> accessoryService.delete(5L));
        Mockito.verify(accessoryRepository, Mockito.times(1)).delete(caseAccessory);
    }
}