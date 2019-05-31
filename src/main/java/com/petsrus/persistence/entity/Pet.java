package com.petsrus.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table (name= "Pet")
@Getter @Setter @ToString
public class Pet {

	@Id
	@Column(name = "obj_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long objId;
	
	@Column(name = "full_name", nullable = false, length = 50 )
	private String fullName;
	
	@Column(name = "color", nullable = true, length = 50 )
	private String color;
	
	@ManyToOne
	@JoinColumn(name = "owner_obj_id", nullable = false)
	private Owner owner;

}
