package com.petsrus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsrus.exception.ResourceNotFoundException;
import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.persistence.service.PetService;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;

@Service
public class PetsrusPetService {
	
	@Autowired
	private PetService petService;
	@Autowired
	private ModelMapper modelMapper;

	public PetDTO getPet(long id) throws ResourceNotFoundException {
		Optional<Pet> pet =  petService.getPet(id);

		
		if( pet.isPresent()) {
			PetDTO petDto = modelMapper.map(pet.get(), PetDTO.class);
			return petDto;
		}else {
			throw new ResourceNotFoundException("Pet not found.");
		}
		
	}

	public ArrayList<PetDTO> getAllPets() throws ResourceNotFoundException {
		
		ArrayList<PetDTO> pets = new ArrayList<PetDTO>();
		
		petService.getAllPets().parallelStream().forEach(pet ->{
			pets.add(modelMapper.map(pet, PetDTO.class));
		});
		
		if(pets.size()==0) {
			throw new ResourceNotFoundException("No pets found.");
		}

		return pets;

	}

	public Long createPet(PetDTO petDto) {
		Pet pet = new Pet();
		modelMapper.map(petDto, pet);
		return petService.createPet(pet);
	}

	public Long updatePet(PetDTO petDto) throws ResourceNotFoundException {
		
		if(petService.getPet(petDto.getId()).isPresent()) {
			Pet pet = new Pet();
			modelMapper.map(petDto, pet);
			return petService.updatePet(pet);
		}else {
			
			throw new ResourceNotFoundException("Pet not found.");
		}

	}

	public Long deletePet(PetDTO petDto) throws ResourceNotFoundException {
		if (petService.getPet(petDto.getId()).isPresent()) {

			 petService.deletePet(petDto.getId());
			 return petDto.getId();
		} else {

			throw new ResourceNotFoundException("Pet not found.");
		}
	}
	
	
	

}
