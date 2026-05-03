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

    public Category updateCategory(Long id, Category newCategory) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category != null) {
            category.setName(newCategory.getName());
            category.setDescription(newCategory.getDescription());
            return categoryRepository.save(category);
        }

        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}