package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.momentum.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
