package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
