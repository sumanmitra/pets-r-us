package com.petsrus.persistence.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.persistence.repository.OwnerRepository;
import com.petsrus.persistence.repository.PetRepository;
import com.petsrus.util.TestUtil;


@RunWith(SpringRunner.class)
public class PersistenceServiceTest {
	
    @TestConfiguration
    static class PersistenceServiceTestContextConfiguration {
  
        @Bean
        public OwnerService ownerService() {
            return new OwnerService();
        }
        
        @Bean
        public PetService petService() {
        	return new PetService();
        }
    }
	
	
	 
	@Autowired
	private OwnerService ownerService;
	@Autowired
	private PetService petService;
	@MockBean
	private OwnerRepository ownerRepository;
	@MockBean
	private PetRepository petRepository;
	
	@Test
	public void test_createOwner() {

		Owner owner = TestUtil.createOwner();
		
		Mockito.when(ownerRepository.save(owner)).thenReturn(owner);
		
		Long objId = ownerService.createOwner(owner);

		assertThat(objId)
	      .isEqualTo(4L);
	}
	
	@Test
	public void test_updateOwner() {

		Owner owner = TestUtil.createOwner();
		
		Mockito.when(ownerRepository.save(owner)).thenReturn(owner);
		
		Long objId = ownerService.updateOwner(owner);

		assertThat(objId)
	      .isEqualTo(4L);
	
		
	}
	
	@Test
	public void test_getOwner() {
		
		Owner owner = TestUtil.createOwner();
		
		Mockito.when(ownerRepository.findById(owner.getObjId())).thenReturn(Optional.of(owner));
		
		Owner ownerFromDB = ownerService.getOwner(4L).get();
		
		assertThat(ownerFromDB.getFullName())
	      .isEqualTo(owner.getFullName());
		
		assertThat(ownerFromDB.getAge())
	      .isEqualTo(owner.getAge());
		
		assertThat(ownerFromDB.toString())
	      .isEqualTo(owner.toString());
		
	}
	@Test
	public void test_getAllOwners() {
		Owner owner = TestUtil.createOwner();
		List<Owner> owners = new ArrayList<Owner>();
		owners.add(owner);
		
		Mockito.when(ownerRepository.findAll()).thenReturn(owners);
		
		List<Owner> ownersFromDB = ownerService.getAllOwners();
		
		assertThat(ownersFromDB.size()).isEqualTo(1);
	}
	
	@Test
	public void test_deleteOwner() {
		
		ownerService.deleteOwner(4L);
		
		verify(ownerRepository, times(1)).deleteById(4L); 
		
	}
	
	@Test
	public void test_createPet() {

		Pet pet = TestUtil.createPet();
		
		Mockito.when(petRepository.save(pet)).thenReturn(pet);
		
		Long objId = petService.createPet(pet);

		assertThat(objId)
	      .isEqualTo(pet.getObjId());
	}
	
	@Test
	public void test_updatePet() {

		Pet pet = TestUtil.createPet();
		
		Mockito.when(petRepository.saveAndFlush(pet)).thenReturn(pet);
		
		Long objId = petService.updatePet(pet);

		assertThat(objId)
	      .isEqualTo(pet.getObjId());
	
		
	}
	
	@Test
	public void test_getPet() {
		
		Pet pet = TestUtil.createPet();
		
		Mockito.when(petRepository.findById(pet.getObjId())).thenReturn(Optional.of(pet));
		
		Pet petFromDB = petService.getPet(pet.getObjId()).get();
		
		assertThat(petFromDB.getFullName())
	      .isEqualTo(pet.getFullName());
		
		assertThat(petFromDB.getOwner().getFullName())
	      .isEqualTo(pet.getOwner().getFullName());
		
		assertThat(petFromDB.toString())
	      .isEqualTo(pet.toString());
		
	}
	@Test
	public void test_getAllPets() {
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		
		Mockito.when(petRepository.findAll()).thenReturn(pets);
		
		List<Pet> petsFromDB = petService.getAllPets();
		
		assertThat(petsFromDB.size()).isEqualTo(1);
	}


	
	@Test
	public void test_deletePet() {
		
		petService.deletePet(1L);
		
		verify(petRepository, times(1)).deleteById(1L); 
		
	}
	
	@Test
	public void test_getPetsByOwnerName() {
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		
		Mockito.when(petRepository.findAllByOwnerFullName(pet.getOwner().getFullName())).thenReturn(pets);
		
		List<Pet> petsFromDB = petService.getPets(pet.getOwner().getFullName());
		
		assertThat(petsFromDB.size()).isEqualTo(1);
		
	}
	
	@Test
	public void test_getPetsByOwner() {
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		
		Mockito.when(petRepository.findAllByOwnerObjId(pet.getOwner().getObjId())).thenReturn(pets);
		
		List<Pet> petsFromDB = petService.getPets(pet.getOwner());
		
		assertThat(petsFromDB.size()).isEqualTo(1);
		assertThat(petsFromDB.get(0).getColor()).isEqualTo(pet.getColor());
		
	}

}
