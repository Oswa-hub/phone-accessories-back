package phone_accessories.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import phone_accessories.app.entity.Accessory;
import phone_accessories.app.entity.Brand;
import phone_accessories.app.entity.Category;
import phone_accessories.app.repository.AccessoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @CacheEvict(value = "accessories", allEntries = true)
    public Accessory update(Long id, Accessory updated, Long categoryId, Long brandId) {
        Accessory existing = getById(id);
        Category category = categoryService.getById(categoryId);
        Brand brand = brandService.getById(brandId);
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setCategory(category);
        existing.setBrand(brand);
        return accessoryRepository.save(existing);
    }

    @CacheEvict(value = "accessories", allEntries = true)
    public Accessory createAccessory(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    @Cacheable("accessories")
    public List<Accessory> filterAccessories(Long categoryId, Long brandId) {
        System.out.println("Fetching from DATABASE...");
        if (categoryId != null && brandId != null)
            return accessoryRepository.findByCategoryIdAndBrandId(categoryId, brandId);
        if (categoryId != null)
            return accessoryRepository.findByCategoryId(categoryId);
        if (brandId != null)
            return accessoryRepository.findByBrandId(brandId);
        return accessoryRepository.findAll();
    }

    @CacheEvict(value = "accessories", allEntries = true)
    public void delete(Long id) {
        accessoryRepository.delete(getById(id));
    }

    @Cacheable(value = "accessories", key = "#id")
    public Accessory getById(Long id) {
        System.out.println("Fetching accessory by ID from DATABASE...");
        return accessoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accessory not found with id: " + id));
    }
}