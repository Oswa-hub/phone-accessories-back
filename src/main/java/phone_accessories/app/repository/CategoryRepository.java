package phone_accessories.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phone_accessories.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
