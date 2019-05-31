package com.petsrus.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.repository.OwnerRepository;

@Service
@Transactional
public class OwnerService {

	
	@Autowired
	private OwnerRepository ownerRepository;

	public List<Owner> getAllOwners(){
		return ownerRepository.findAll();
	}
	
	public Long createOwner(Owner owner) {
		return ownerRepository.save(owner).getObjId();
	}
	
	public Long updateOwner(Owner owner) {
		return ownerRepository.save(owner).getObjId();
	}
	
	public Optional<Owner> getOwner(long id) {
		return ownerRepository.findById(id);
	}

	public void deleteOwner(Long id) {
		 ownerRepository.deleteById(id);
	}
}
