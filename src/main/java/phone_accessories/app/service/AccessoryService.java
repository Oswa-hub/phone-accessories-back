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

    // View all
    public List<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }
}
