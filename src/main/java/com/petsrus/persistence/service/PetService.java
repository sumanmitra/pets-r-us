package com.petsrus.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.persistence.repository.OwnerRepository;
import com.petsrus.persistence.repository.PetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetService {

	
	
	@Autowired
	private PetRepository petRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	
	
	public Optional<Pet> getPet(Long id) {
		
		return petRepository.findById(id);
	}
	
	public List<Pet> getAllPets(){
		return petRepository.findAll();
	}
	
	public List<Pet> getPets(Owner owner){
		return petRepository.findAllByOwnerObjId(owner.getObjId());
	}
	
	public List<Pet> getPets(String ownerName){
		return petRepository.findAllByOwnerFullName(ownerName);
	}
	
	public void deletePet(long id) {
		petRepository.deleteById(id);
	}
	
	public Long createPet(Pet pet) {
		return petRepository.save(pet).getObjId();
	}
	
	public Long updatePet(Pet pet) {
		return petRepository.saveAndFlush(pet).getObjId();
	}
	
}
