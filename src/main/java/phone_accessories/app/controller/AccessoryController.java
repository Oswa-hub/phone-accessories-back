package phone_accessories.app.controller;



import phone_accessories.app.entity.Accessory;
import phone_accessories.app.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<Accessory> update(
            @PathVariable Long id,
            @RequestBody Accessory accessory,
            @RequestParam Long categoryId,
            @RequestParam Long brandId) {
        return ResponseEntity.ok(accessoryService.update(id, accessory, categoryId, brandId));
    }
}