package com.petsrus.service.dto;

import com.petsrus.service.Colors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @NoArgsConstructor @ToString
public class PetDTO {
	
	private Long id;
	private String fullName;
	private Colors color;
	private OwnerDTO owner;

}
