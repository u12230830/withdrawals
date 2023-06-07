package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
}
