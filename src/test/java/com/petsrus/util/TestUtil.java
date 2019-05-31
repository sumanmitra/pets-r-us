package com.petsrus.util;

import com.petsrus.persistence.entity.Owner;
import com.petsrus.persistence.entity.Pet;
import com.petsrus.service.Colors;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;

public class TestUtil {
	
	public static Owner createOwner() {
		Owner owner = new Owner();
		owner.setObjId(4L);
		owner.setFullName("Bill Gates");
		owner.setAge((short) 50);
		return owner;
	}
	
	public static Pet createPet() {
		Pet pet = new Pet();
		pet.setObjId(1L);
		pet.setFullName("Lucy");
		pet.setColor(Colors.BLACK.name());
		pet.setOwner(createOwner());
		return pet;
	}

	public static OwnerDTO createOwnerDto() {
		OwnerDTO ownerDto = new OwnerDTO();
		ownerDto.setId(4L);
		ownerDto.setFullName("Suman Mitra");
		ownerDto.setAge((short) 20);
		return ownerDto;
	}

	public static PetDTO createPetDto() {
		PetDTO petDto = new PetDTO();
		petDto.setId(1L);
		petDto.setFullName("Lucy");
		petDto.setColor(Colors.BLACK);
		petDto.setOwner(createOwnerDto());
		return petDto;
	}

}
