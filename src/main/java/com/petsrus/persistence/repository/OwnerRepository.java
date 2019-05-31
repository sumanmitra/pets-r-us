package com.petsrus.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsrus.persistence.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {


}
