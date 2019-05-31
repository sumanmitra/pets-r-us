package com.petsrus.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class OwnerDTO {
	
	private Long id;
	private String fullName;
	private short age;

}
