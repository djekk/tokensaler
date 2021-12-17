package com.syqu.shop.repository;

import com.syqu.shop.domain.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
	Distributor findByUsername(String username);
    Distributor findByEmail(String email);
    Distributor findById(long id);
}
