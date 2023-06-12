package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.momentum.model.WithdrawalStatus;

public interface WithdrawalStatusRepository extends JpaRepository<WithdrawalStatus, Integer> {
}
