package com.petsrus.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsrus.persistence.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
	
	List<Pet> findAllByOwnerObjId(long id);
	List<Pet> findAllByOwnerFullName(String name);

}
