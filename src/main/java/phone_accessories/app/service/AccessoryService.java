package phone_accessories.app.service;



import phone_accessories.app.entity.Accessory;
import phone_accessories.app.entity.Brand;

import phone_accessories.app.entity.Category;
import phone_accessories.app.repository.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final CategoryService categoryService;
    private final BrandService brandService;









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

    public void delete(Long id) {
        Accessory existing = getById(id);
        accessoryRepository.delete(existing);
    }
}