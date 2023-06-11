package za.co.momentum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.momentum.model.Withdrawal;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Integer> {
}
