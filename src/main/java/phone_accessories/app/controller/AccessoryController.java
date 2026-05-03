package phone_accessories.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import phone_accessories.app.entity.Accessory;
import phone_accessories.app.service.AccessoryService;

import java.util.List;

@RestController
@RequestMapping("/api/accessories")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    // POST
    @PostMapping
    public Accessory createAccessory(@RequestBody Accessory accessory) {
        return accessoryService.createAccessory(accessory);
    }

    // GET
    @GetMapping
    public List<Accessory> getAllAccessories() {
        return accessoryService.getAllAccessories();
    }
}
