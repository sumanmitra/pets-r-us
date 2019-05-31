package com.petsrus.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.petsrus.exception.ResourceNotFoundException;
import com.petsrus.service.Colors;
import com.petsrus.service.PetsrusOwnerService;
import com.petsrus.service.PetsrusPetService;
import com.petsrus.service.dto.OwnerDTO;
import com.petsrus.service.dto.PetDTO;
import com.petsrus.util.TestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(PetsrusController.class)
public class PetsrusControllerTest {

	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private PetsrusOwnerService ownerService;
	@MockBean
	private PetsrusPetService petService;
	
	
	
	@Test
	public void test_getOwner() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		
		given(ownerService.getOwner(4L)).willReturn(ownerDto);
		
		mvc.perform(get("/v1/petsrus/owners/{id}","4")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("fullName", is(ownerDto.getFullName())));
	}
	
	@Test
	public void test_getOwner_resourceNotFound() throws Exception {
		
		given(ownerService.getOwner(5L)).willThrow(new ResourceNotFoundException("Owner not found."));
		
		mvc.perform(get("/v1/petsrus/owners/{id}","5")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("errorMessage", is("Owner not found.")));
	}
	
	@Test
	public void test_getOwner_InvalidIdentifier() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		
		given(ownerService.getOwner(4L)).willReturn(ownerDto);
		
		mvc.perform(get("/v1/petsrus/owners/{id}","5A")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("errorMessage", is("For input string: \"5A\"")));
	}
	
	@Test
	public void test_getAllOwner() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		ArrayList<OwnerDTO> owners = new ArrayList<OwnerDTO>();
		owners.add(ownerDto);
		
		given(ownerService.getOwners()).willReturn(owners);
		
		mvc.perform(get("/v1/petsrus/owners")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].fullName", is(ownerDto.getFullName())));
	}
	
	@Test
	public void test_getAllOwner_WithWrongRequestMethod() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		ArrayList<OwnerDTO> owners = new ArrayList<OwnerDTO>();
		owners.add(ownerDto);
		
		given(ownerService.getOwners()).willReturn(owners);
		
		mvc.perform(post("/v1/petsrus/owners")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isMethodNotAllowed())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("errorMessage", is("Request method 'POST' not supported")));
	}
	
	@Test
	public void test_getPetsByOwner() throws Exception {
		PetDTO petDto = TestUtil.createPetDto();
		ArrayList<PetDTO> pets = new ArrayList<PetDTO>();
		pets.add(petDto);
		
		given(ownerService.getPets("4")).willReturn(pets);
		
		mvc.perform(get("/v1/petsrus/owners/{id}/pets", "4")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].fullName", is(petDto.getFullName())));
	}
	
	@Test
	public void test_getPet() throws Exception {
		PetDTO petDto = TestUtil.createPetDto();
		
		given(petService.getPet(1L)).willReturn(petDto);
		
		mvc.perform(get("/v1/petsrus/pets/{id}","1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("fullName", is(petDto.getFullName())));
	}
	
	@Test
	public void test_getAllPet() throws Exception {
		PetDTO petDto = TestUtil.createPetDto();
		ArrayList<PetDTO> pets = new ArrayList<PetDTO>();
		pets.add(petDto);
		
		given(petService.getAllPets()).willReturn(pets);
		
		mvc.perform(get("/v1/petsrus/pets")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].fullName", is(petDto.getFullName())));
	}
	
	@Test
	public void test_createOwner() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		Gson gson = new Gson();
		
		
		mvc.perform( MockMvcRequestBuilders
			      .post("/v1/petsrus/owner")
			      .content(gson.toJson(ownerDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated());
		
	}
	
	@Test
	public void test_createPet() throws Exception {

		mvc.perform( MockMvcRequestBuilders
			      .post("/v1/petsrus/pet")
			      .content("{\"fullName\": \"Kalu\", \"color\": \"brown\",\"owner\": {\"id\": 1} }")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated());
		
	}
	
	@Test
	public void test_createPet_withInvalidColor() throws Exception {
		
		mvc.perform( MockMvcRequestBuilders
			      .post("/v1/petsrus/pet")
			      .content("{\"fullName\": \"Kalu\", \"color\": \"red\",\"owner\": {\"id\": 1} }")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void test_updateOwner() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		Gson gson = new Gson();
		
		
		mvc.perform( MockMvcRequestBuilders
			      .put("/v1/petsrus/owner")
			      .content(gson.toJson(ownerDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
	}
	
	@Test
	public void test_updatePet() throws Exception {
		PetDTO petDto = TestUtil.createPetDto();
		Gson gson = new Gson();
		
		
		mvc.perform( MockMvcRequestBuilders
			      .put("/v1/petsrus/pet")
			      .content(gson.toJson(petDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
	}
	
	@Test
	public void test_DeleteOwner() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		Gson gson = new Gson();
		
		
		mvc.perform( MockMvcRequestBuilders
			      .delete("/v1/petsrus/owner")
			      .content(gson.toJson(ownerDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isAccepted());
		
	}
	
	@Test
	public void test_DeletePet() throws Exception {
		PetDTO petDto = TestUtil.createPetDto();
		Gson gson = new Gson();
		
		
		mvc.perform( MockMvcRequestBuilders
			      .delete("/v1/petsrus/pet")
			      .content(gson.toJson(petDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isAccepted());
		
	}
	
	
	/*@Test
	public void test_createOwner_WithNullValue() throws Exception {
		OwnerDTO ownerDto = TestUtil.createOwnerDto();
		ownerDto.setFullName(null);
		Gson gson = new Gson();
		String errorMessage = "could not execute statement; SQL [n/a]; constraint [null]; "
				+ "nested exception is org.hibernate.exception.ConstraintViolationException:"
				+ " could not execute statement";

	//	when(ownerService.createOwner(ownerDto)).thenThrow(new DataIntegrityViolationException(errorMessage));
		given(ownerService.createOwner(ownerDto)).willThrow(new DataIntegrityViolationException(errorMessage));
		
		mvc.perform( MockMvcRequestBuilders
			      .post("/v1/petsrus/owner")
			      .content(gson.toJson(ownerDto))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest());
		
	}*/
	
	
	
}
