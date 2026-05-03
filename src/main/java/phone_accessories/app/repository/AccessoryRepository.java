package phone_accessories.app.repository;



import phone_accessories.app.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    List<Accessory> findByCategoryId(Long categoryId);
    List<Accessory> findByBrandId(Long brandId);
}