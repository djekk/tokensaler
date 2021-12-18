package com.syqu.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syqu.shop.object.Distributor;

@Repository
public interface DistributorRepository extends JpaRepository<Distributor, Long> {
	Distributor findByUsername(String username);
    Distributor findByEmail(String email);
    Distributor findById(long id);
}
