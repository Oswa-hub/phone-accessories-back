package phone_accessories.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import phone_accessories.app.entity.Brand;
import phone_accessories.app.repository.BrandRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository; // Fakes the database layer

    @InjectMocks
    private BrandService brandService; // Injects the fake repository into the real service

    private Brand apple;

    @BeforeEach
    void setUp() {
        apple = Brand.builder().id(1L).name("Apple").build();
    }

    @Test
    void getAll_ShouldReturnBrandList() {
        Mockito.when(brandRepository.findAll()).thenReturn(Arrays.asList(apple));

        List<Brand> result = brandService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Apple", result.get(0).getName());
    }

    @Test
    void getById_ShouldReturnBrand_WhenIdExists() {
        Mockito.when(brandRepository.findById(1L)).thenReturn(Optional.of(apple));

        Brand result = brandService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Apple", result.getName());
    }

    @Test
    void create_ShouldSaveAndReturnBrand() {
        Mockito.when(brandRepository.save(any(Brand.class))).thenReturn(apple);

        Brand result = brandService.create(Brand.builder().name("Apple").build());

        assertNotNull(result);
        assertEquals("Apple", result.getName());
    }
}