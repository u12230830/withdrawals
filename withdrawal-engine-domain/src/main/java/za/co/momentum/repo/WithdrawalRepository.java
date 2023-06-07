package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Integer> {
}
