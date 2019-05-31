package com.petsrus.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.petsrus.config.AppConfig;
import com.petsrus.exception.ResourceNotFoundException;
import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.persistence.service.OwnerService;
import com.petsrus.persistence.service.PetService;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;
import com.petsrus.util.TestUtil;


@RunWith(SpringRunner.class)
public class PetsrusServiceTest {
	
    @TestConfiguration
    static class BusinessServiceTestContextConfiguration extends AppConfig{
  
        @Bean
        public PetsrusOwnerService petsrusOwnerService() {
            return new PetsrusOwnerService();
        }
        
        @Bean
        public PetsrusPetService petsrusPetService() {
        	return new PetsrusPetService();
        }
    }
    
	@Autowired
	private PetsrusOwnerService petsrusOwnerService;
	@Autowired
	private PetsrusPetService petsrusPetService;
	@Autowired
	private ModelMapper modelMapper;
	@MockBean
	private OwnerService ownerService;
	@MockBean
	private PetService petService;
	
	@Test
	public void test_getOwner() throws ResourceNotFoundException {
		Owner owner = TestUtil.createOwner();
		OwnerDTO ownerDto = null;
		
		Mockito.when(ownerService.getOwner(owner.getObjId())).thenReturn(Optional.of(owner));
		

			 ownerDto = petsrusOwnerService.getOwner(owner.getObjId());
			 assertThat(owner.getFullName()).isEqualTo(ownerDto.getFullName());

	}
	@Test
	public void test_whenOwnerNotFound() {
		Owner owner = TestUtil.createOwner();

		
		Mockito.when(ownerService.getOwner(owner.getObjId())).thenReturn(Optional.empty());
		
		try {
			 petsrusOwnerService.getOwner(owner.getObjId());

		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Owner not found.");
		}
		
	}
	
	@Test
	public void test_getOwners() throws ResourceNotFoundException {
		Owner owner = TestUtil.createOwner();
		List<Owner> owners = new ArrayList<Owner>();
		owners.add(owner);
		OwnerDTO ownerDto = null;
		
		Mockito.when(ownerService.getAllOwners()).thenReturn(owners);
		

			 ownerDto = petsrusOwnerService.getOwners().get(0);
			 assertThat(owner.getFullName()).isEqualTo(ownerDto.getFullName());

		
	}
	@Test
	public void test_whenOwnersNotFound() {
		List<Owner> owners = new ArrayList<Owner>();
		
		Mockito.when(ownerService.getAllOwners()).thenReturn(owners);
		
		try {
			 petsrusOwnerService.getOwners();

		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("No owners found.");
		}
		
	}
	
	@Test
	public void test_getPetsByOwner() throws ResourceNotFoundException {
		
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		
		Mockito.when(ownerService.getOwner(pet.getOwner().getObjId())).thenReturn(Optional.of(pet.getOwner()));
		Mockito.when(petService.getPets(pet.getOwner())).thenReturn(pets);
		

			List<PetDTO> petsFromDB = petsrusOwnerService.getPets(String.valueOf(pet.getOwner().getObjId()));
			assertThat(petsFromDB.get(0).getColor().name()).isEqualTo(pet.getColor());
			assertThat(petsFromDB.get(0).getFullName()).isEqualTo(pet.getFullName());
			assertThat(petsFromDB.get(0).getId()).isEqualTo(pet.getObjId());
			assertThat(petsFromDB.get(0).getOwner().getFullName()).isEqualTo(pet.getOwner().getFullName());
			assertThat(petsFromDB.get(0).toString().contains("Lucy")).isEqualTo(true);

	}
	
	@Test
	public void test_getPetsByOwner_WhenOwnerNotFound() {
		
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		
		Mockito.when(ownerService.getOwner(0L)).thenReturn(Optional.empty());
	
		try {
			petsrusOwnerService.getPets(String.valueOf(0L));
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Owner not found.");
		}
	}
	
	@Test
	public void test_getPetsByOwner_whenPetsNotFound() {
		
		Owner owner = TestUtil.createOwner();
		owner.setObjId(5L);;
		List<Pet> pets = new ArrayList<Pet>();
		
		Mockito.when(ownerService.getOwner(5L)).thenReturn(Optional.of(owner));
		Mockito.when(petService.getPets(owner)).thenReturn(pets);
		
		try {
			petsrusOwnerService.getPets(String.valueOf(5L));
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("No pets found.");
		}
	}
	
	@Test
	public void test_createOwner() {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		
		Mockito.when(ownerService.createOwner((Owner) (any(Owner.class)))).thenReturn(ownerDto.getId());
		Long objId = petsrusOwnerService.createOwner(ownerDto);
		
		assertThat(objId).isEqualTo(ownerDto.getId());
	}
	
	@Test
	public void test_updateOwner() throws ResourceNotFoundException {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();

		Mockito.when(ownerService.getOwner(ownerDto.getId())).thenReturn(Optional.of(new Owner()));
		Mockito.when(ownerService.updateOwner((Owner) (any(Owner.class)))).thenReturn(ownerDto.getId());
		Long objId = petsrusOwnerService.updateOwner(ownerDto);
		
		assertThat(objId).isEqualTo(ownerDto.getId());
	}
	
	@Test
	public void test_updateOwner_WhenOwnerNotFound() {
		
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		
		Mockito.when(ownerService.getOwner(ownerDto.getId())).thenReturn(Optional.empty());
	
		try {
			petsrusOwnerService.updateOwner(ownerDto);
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Owner not found.");
		}
	}
	
	@Test
	public void test_deleteOwner() throws ResourceNotFoundException {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();

		when(ownerService.getOwner(ownerDto.getId())).thenReturn(Optional.of(new Owner()));
		doNothing().when (ownerService).deleteOwner(ownerDto.getId());
		Long objId = petsrusOwnerService.deleteOwner(ownerDto);
		
		verify(ownerService, times(1)).deleteOwner(ownerDto.getId()); 
		assertThat(objId).isEqualTo(ownerDto.getId());
	}
	
	@Test
	public void test_deleteOwner_WhenOwnerNotFound() {
		
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		
		Mockito.when(ownerService.getOwner(ownerDto.getId())).thenReturn(Optional.empty());
	
		try {
			petsrusOwnerService.deleteOwner(ownerDto);
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Owner not found.");
		}
	}
	
	
	@Test
	public void test_getPet() throws ResourceNotFoundException {
		Pet pet = TestUtil.createPet();
		PetDTO petDto = null;
		
		Mockito.when(petService.getPet(pet.getObjId())).thenReturn(Optional.of(pet));
		

			 petDto = petsrusPetService.getPet(pet.getObjId());
			 assertThat(pet.getFullName()).isEqualTo(petDto.getFullName());
		
		
	}
	@Test
	public void test_whenPetNotFound() {
		
		Pet pet = TestUtil.createPet();

		
		Mockito.when(petService.getPet(pet.getObjId())).thenReturn(Optional.empty());
		
		try {
			 petsrusPetService.getPet(pet.getObjId());

		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Pet not found.");
		}
		
	}
	
	@Test
	public void test_getPets() throws ResourceNotFoundException {
		Pet pet = TestUtil.createPet();
		List<Pet> pets = new ArrayList<Pet>();
		pets.add(pet);
		PetDTO petDto = null;
		
		Mockito.when(petService.getAllPets()).thenReturn(pets);
		

			 petDto = petsrusPetService.getAllPets().get(0);
			 assertThat(pet.getFullName()).isEqualTo(petDto.getFullName());

		
	}
	@Test
	public void test_whenPetsNotFound() {
		List<Pet> pets = new ArrayList<Pet>();
		
		Mockito.when(petService.getAllPets()).thenReturn(pets);
		
		try {
			 petsrusPetService.getAllPets();

		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("No pets found.");
		}
		
	}
	
	
	@Test
	public void test_createPet() {
		PetDTO petDto = TestUtil.createPetDto();
		
		Mockito.when(petService.createPet((Pet) (any(Pet.class)))).thenReturn(petDto.getId());
		Long objId = petsrusPetService.createPet(petDto);
		
		assertThat(objId).isEqualTo(petDto.getId());
	}
	
	@Test
	public void test_updatePet() throws ResourceNotFoundException {
		PetDTO petDto = TestUtil.createPetDto();

		Mockito.when(petService.getPet(petDto.getId())).thenReturn(Optional.of(new Pet()));
		Mockito.when(petService.updatePet((Pet) (any(Pet.class)))).thenReturn(petDto.getId());
		Long objId = petsrusPetService.updatePet(petDto);
		
		assertThat(objId).isEqualTo(petDto.getId());
	}
	
	@Test
	public void test_updatePet_WhenPetNotFound() {
		
		PetDTO petDto = TestUtil.createPetDto();
		
		Mockito.when(petService.getPet(petDto.getId())).thenReturn(Optional.empty());
	
		try {
			petsrusPetService.updatePet(petDto);
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Pet not found.");
		}
	}

	@Test
	public void test_deletePet() throws ResourceNotFoundException {
		PetDTO petDto = TestUtil.createPetDto();

		when(petService.getPet(petDto.getId())).thenReturn(Optional.of(new Pet()));
		doNothing().when (petService).deletePet(petDto.getId());
		Long objId = petsrusPetService.deletePet(petDto);
		
		verify(petService, times(1)).deletePet(petDto.getId()); 
		assertThat(objId).isEqualTo(petDto.getId());
	}
	
	@Test
	public void test_deletePet_WhenOwnerNotFound() {
		
		PetDTO petDto = TestUtil.createPetDto();
		
		Mockito.when(petService.getPet(petDto.getId())).thenReturn(Optional.empty());
	
		try {
			petsrusPetService.deletePet(petDto);
		} catch (ResourceNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Pet not found.");
		}
	}
}
