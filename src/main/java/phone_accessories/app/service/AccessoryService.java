package phone_accessories.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phone_accessories.app.entity.Accessory;
import phone_accessories.app.repository.AccessoryRepository;

import java.util.List;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    // Create
    public Accessory createAccessory(Accessory accessory) {
        return accessoryRepository.save(accessory);
    }

    // Filter + View
    public List<Accessory> filterAccessories(Long categoryId, Long brandId) {

        if (categoryId != null && brandId != null) {
            return accessoryRepository.findByCategoryIdAndBrandId(categoryId, brandId);
        }

        if (categoryId != null) {
            return accessoryRepository.findByCategoryId(categoryId);
        }

        if (brandId != null) {
            return accessoryRepository.findByBrandId(brandId);
        }

        return accessoryRepository.findAll();
    }

    // GET BY ID
    public Accessory getById(Long id) {
        return accessoryRepository.findById(id).orElse(null);
    }
}