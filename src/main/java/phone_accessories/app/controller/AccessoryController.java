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

    // GET (filter + view all)
    @GetMapping
    public List<Accessory> getAccessories(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId) {

        return accessoryService.filterAccessories(categoryId, brandId);
    }
}git add .