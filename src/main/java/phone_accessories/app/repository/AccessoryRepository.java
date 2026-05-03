package phone_accessories.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phone_accessories.app.entity.Accessory;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}
