package phone_accessories.app.service;


import phone_accessories.app.entity.Brand;

import phone_accessories.app.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAll() {
        return brandRepository.findAll();
    }

    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
    }

    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand update(Long id, Brand updated) {
        Brand existing = getById(id);
        existing.setName(updated.getName());
        return brandRepository.save(existing);
    }

    public void delete(Long id) {
        Brand existing = getById(id);
        brandRepository.delete(existing);
    }
}