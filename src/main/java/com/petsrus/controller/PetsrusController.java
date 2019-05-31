package com.petsrus.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.petsrus.exception.ResourceNotFoundException;
import com.petsrus.service.PetsrusOwnerService;
import com.petsrus.service.PetsrusPetService;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;

@RestController
@RequestMapping("/v1/petsrus")
public class PetsrusController {
	
	@Autowired
	private PetsrusOwnerService ownerService;
	@Autowired
	private PetsrusPetService petService;
	
	@RequestMapping(value="/owners/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getOwner(@PathVariable String id) throws  ResourceNotFoundException {
		
		return new ResponseEntity<>(ownerService.getOwner(Long.parseLong(id)),HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/owners", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ArrayList<OwnerDTO>> getAllOwners() throws ResourceNotFoundException {
		
		return new ResponseEntity<>(ownerService.getOwners(),HttpStatus.OK);
		
	}
	
	@RequestMapping(value ="/owners/{id}/pets", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getPetsByOwner(@PathVariable String id) throws ResourceNotFoundException{
		return new ResponseEntity<>(ownerService.getPets(id), HttpStatus.OK); 
	}
	
	@RequestMapping(value="/pets/{id}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getPet(@PathVariable String id) throws  ResourceNotFoundException {
		
		return new ResponseEntity<>(petService.getPet(Long.parseLong(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/pets", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object>  getAllPets() throws ResourceNotFoundException{
		return new ResponseEntity<>(petService.getAllPets(), HttpStatus.OK);
	}
	
	@PostMapping(value="/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createOwner(@RequestBody OwnerDTO ownerDto){
        return new ResponseEntity<>(ownerService.createOwner(ownerDto), HttpStatus.CREATED);
    }
	
	@PostMapping(value="/pet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createPet(@RequestBody PetDTO ownerDto){
        return new ResponseEntity<>(petService.createPet(ownerDto), HttpStatus.CREATED);
    }
	
	@PutMapping(value="/owner")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateOwner(@RequestBody OwnerDTO ownerDto) throws ResourceNotFoundException{
        return new ResponseEntity<>(ownerService.updateOwner(ownerDto), HttpStatus.OK);
    }

	@PutMapping(value="/pet")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updatePet(@RequestBody PetDTO petDto) throws ResourceNotFoundException{
        return new ResponseEntity<>(petService.updatePet(petDto), HttpStatus.OK);
    }
	
	@DeleteMapping(value="/owner")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> DeleteOwner(@RequestBody OwnerDTO ownerDto) throws ResourceNotFoundException{
        return new ResponseEntity<>(ownerService.deleteOwner(ownerDto), HttpStatus.ACCEPTED);
    }

	@DeleteMapping(value="/pet")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> DeletePet(@RequestBody PetDTO petDto) throws ResourceNotFoundException{
        return new ResponseEntity<>(petService.deletePet(petDto), HttpStatus.ACCEPTED);
    }
}
