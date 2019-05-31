package com.petsrus.persistence.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;




@Entity
@Table (name= "Owner")
@Getter @Setter @ToString
public class Owner {
	
	@Id
	@Column(name = "obj_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long objId;
	
	@Column(name = "full_name", nullable = false, length = 50 )
	private String fullName;
	
	@Column(name = "age", nullable = true)
	private short age;

	

}
