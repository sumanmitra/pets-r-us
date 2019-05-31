package com.petsrus.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.persistence.repository.OwnerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaRepositoryTest {
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private PetRepository petRepository;
	
	
	@Test
	public void whenFindByOwnerId_thenReturnOwner() {
		
		Owner owner = new Owner();
		owner.setFullName("Bill Gates");
		owner.setAge((short) 50);
		
		testEntityManager.merge(owner);
		testEntityManager.flush();
		// three records are already inserted in the db from data.sql
		Owner foundOwner = ownerRepository.findById(4L).get();
		
		assertThat(foundOwner.getFullName())
	      .isEqualTo(owner.getFullName());
	}
	
	@Test
	public void whenFindByPetId_thenReturnPet() {
		

		Pet foundPet = petRepository.findById(4L).get();
		
		assertThat(foundPet.getFullName())
	      .isEqualTo("Rocky");
	}
	

}
