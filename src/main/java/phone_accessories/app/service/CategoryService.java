package phone_accessories.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phone_accessories.app.entity.Category;
import phone_accessories.app.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Read
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}