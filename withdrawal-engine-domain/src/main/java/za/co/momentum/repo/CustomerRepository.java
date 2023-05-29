package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
