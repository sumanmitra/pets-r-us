package com.petsrus.service;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsrus.exception.ResourceNotFoundException;
import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.service.OwnerService;
import com.petsrus.persistence.service.PetService;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;

@Service
public class PetsrusOwnerService {

	@Autowired
	OwnerService ownerService;
	@Autowired
	PetService petService;

	@Autowired
	ModelMapper modelMapper;

	public OwnerDTO getOwner(long id) throws ResourceNotFoundException {
		OwnerDTO ownerDto = null;

		Optional<Owner> owner = ownerService.getOwner(id);

		if (owner.isPresent()) {
			ownerDto = modelMapper.map(owner.get(), OwnerDTO.class);
		} else {
			throw new ResourceNotFoundException("Owner not found.");
		}
		return ownerDto;
	}

	public ArrayList<OwnerDTO> getOwners() throws ResourceNotFoundException {

		ArrayList<OwnerDTO> owners = new ArrayList<OwnerDTO>();

		ownerService.getAllOwners().parallelStream().forEach(owner -> {
			owners.add(modelMapper.map(owner, OwnerDTO.class));
		});
		if (owners.size() == 0) {
			throw new ResourceNotFoundException("No owners found.");
		}
		return owners;

	}

	public ArrayList<PetDTO> getPets(String ownerId) throws ResourceNotFoundException {

		Optional<Owner> owner = ownerService.getOwner(Long.parseLong(ownerId));
		ArrayList<PetDTO> pets = new ArrayList<PetDTO>();
		if (owner.isPresent()) {
			petService.getPets(owner.get()).parallelStream().forEach(pet -> {
				pets.add(modelMapper.map(pet, PetDTO.class));
			});

		} else {
			throw new ResourceNotFoundException("Owner not found.");
		}
		if (pets.size() == 0) {
			throw new ResourceNotFoundException("No pets found.");
		}

		return pets;

	}

	public Long createOwner(OwnerDTO ownerDto) {

		Owner owner = new Owner();
		modelMapper.map(ownerDto, owner);
		return ownerService.createOwner(owner);
	}

	public Long updateOwner(OwnerDTO ownerDto) throws ResourceNotFoundException {

		if (ownerService.getOwner(ownerDto.getId()).isPresent()) {
			Owner owner = new Owner();
			modelMapper.map(ownerDto, owner);
			return ownerService.updateOwner(owner);
		} else {

			throw new ResourceNotFoundException("Owner not found.");
		}

	}

	public Long deleteOwner(OwnerDTO ownerDto) throws ResourceNotFoundException {
		if (ownerService.getOwner(ownerDto.getId()).isPresent()) {

			 ownerService.deleteOwner(ownerDto.getId());
			 return ownerDto.getId();
		} else {

			throw new ResourceNotFoundException("Owner not found.");
		}
	}

}
