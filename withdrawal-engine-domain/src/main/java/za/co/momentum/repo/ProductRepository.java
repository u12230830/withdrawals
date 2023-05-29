package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
