package phone_accessories.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phone_accessories.app.entity.Accessory;
import phone_accessories.app.service.AccessoryService;

import java.util.List;

@RestController
@RequestMapping("/api/accessories")
@RequiredArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    // DELETE /api/accessories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accessoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST/create
    @PostMapping
    public ResponseEntity<Accessory> createAccessory(@RequestBody Accessory accessory) {
        return ResponseEntity.ok(accessoryService.createAccessory(accessory));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Accessory> update(
            @PathVariable Long id,
            @RequestBody Accessory accessory,
            @RequestParam Long categoryId,
            @RequestParam Long brandId) {

        return ResponseEntity.ok(
                accessoryService.update(id, accessory, categoryId, brandId)
        );
    }

    // GET (filter + view all)
    @GetMapping
    public List<Accessory> getAccessories(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId) {

        return accessoryService.filterAccessories(categoryId, brandId);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Accessory getAccessoryById(@PathVariable Long id) {
        return accessoryService.getById(id);
    }
}