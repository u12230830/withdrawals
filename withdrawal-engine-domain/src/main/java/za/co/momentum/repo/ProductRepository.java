package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.momentum.model.Customer;
import za.co.momentum.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCustomer(Customer customer);
    Product findByAccountNumber(String accountNumber);
}
