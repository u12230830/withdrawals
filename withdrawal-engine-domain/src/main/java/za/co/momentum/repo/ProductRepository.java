package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<List<Product>> findAllByCustomer(Customer customer);

    Optional<Product> findByAccountNumber(String accountNumber);
}
