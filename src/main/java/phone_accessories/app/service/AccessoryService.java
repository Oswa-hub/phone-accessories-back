package phone_accessories.app.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private AccessoryRepository accessoryRepository;
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
    // Create
    public Accessory createAccessory(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    public void delete(Long id) {
        Accessory existing = getById(id);
        accessoryRepository.delete(existing);
    // View all
    public List<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }
}
