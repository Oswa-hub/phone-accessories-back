package phone_accessories.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phone_accessories.app.entity.Accessory;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    List<Accessory> findByCategoryId(Long categoryId);

    List<Accessory> findByBrandId(Long brandId);

    List<Accessory> findByCategoryIdAndBrandId(Long categoryId, Long brandId);
}
