package es.uvigo.esei.daa.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import static es.uvigo.esei.daa.dataset.PetDataset.existentId;
import static es.uvigo.esei.daa.dataset.PetDataset.existentPet;
import static es.uvigo.esei.daa.dataset.PetDataset.newName;
import static es.uvigo.esei.daa.dataset.PetDataset.newPet;
import static es.uvigo.esei.daa.dataset.PetDataset.newOwner;
import static es.uvigo.esei.daa.dataset.PetDataset.pet;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasInternalServerErrorStatus;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.containsPetInAnyOrder;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.equalsToPet;
import static java.util.Arrays.asList;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.PetDAO;
import es.uvigo.esei.daa.entities.Pet;

public class PetResourceUnitTest {
	private PetDAO daoMock;
	private PetResource resource;

	@Before
	public void setUp() throws Exception {
		daoMock = createMock(PetDAO.class);
		resource = new PetResource(daoMock);
	}

	@After
	public void tearDown() throws Exception {
		try {
			verify(daoMock);
		} finally {
			daoMock = null;
			resource = null;
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testList() throws Exception {
		final List<Pet> pet = asList(pet());
		
		expect(daoMock.list()).andReturn(pet);
		
		replay(daoMock);
		
		final Response response = resource.list();
		
		assertThat(response, hasOkStatus());
		assertThat((List<Pet>) response.getEntity(), containsPetInAnyOrder(pet()));
	}

	@Test
	public void testListDAOException() throws Exception {
		expect(daoMock.list()).andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.list();
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testGet() throws Exception {
		final Pet pet = existentPet();
		
		expect(daoMock.get(pet.getId())).andReturn(pet);
	
		replay(daoMock);
		
		final Response response = resource.get(pet.getId());
		
		assertThat(response, hasOkStatus());
		assertThat((Pet) response.getEntity(), is(equalsToPet(pet)));
	}

	@Test
	public void testGetDAOException() throws Exception {
		expect(daoMock.get(anyInt())).andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.get(existentId());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testGetIllegalArgumentException() throws Exception {
		expect(daoMock.get(anyInt())).andThrow(new IllegalArgumentException());
		
		replay(daoMock);
		
		final Response response = resource.get(existentId());
		
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testDelete() throws Exception {
		daoMock.delete(anyInt());
		
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasOkStatus());
	}

	@Test
	public void testDeleteDAOException() throws Exception {
		daoMock.delete(anyInt());
		expectLastCall().andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testDeleteIllegalArgumentException() throws Exception {
		daoMock.delete(anyInt());
		expectLastCall().andThrow(new IllegalArgumentException());
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testModify() throws Exception {
		final Pet pet = existentPet();
		pet.setName(newName());
		pet.setOwner(newOwner());
		
		daoMock.modify(pet);
		
		replay(daoMock);

		final Response response = resource.modify(
			pet.getId(), pet.getName(), pet.getOwner());
		
		assertThat(response, hasOkStatus());
		assertEquals(pet, response.getEntity());
	}

	@Test
	public void testModifyDAOException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new DAOException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newOwner());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testModifyIllegalArgumentException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new IllegalArgumentException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newOwner());
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testModifyNullPointerException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new NullPointerException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newOwner());
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testAdd() throws Exception {
		expect(daoMock.add(newName(), newOwner()))
			.andReturn(newPet());
		replay(daoMock);
		

		final Response response = resource.add(newName(), newOwner());
		
		assertThat(response, hasOkStatus());
		assertThat((Pet) response.getEntity(), is(equalsToPet(newPet())));
	}

	@Test
	public void testAddDAOException() throws Exception {
		expect(daoMock.add(anyString(), anyString()))
			.andThrow(new DAOException());
		replay(daoMock);

		final Response response = resource.add(newName(), newOwner());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testAddIllegalArgumentException() throws Exception {
		expect(daoMock.add(anyString(), anyString()))
			.andThrow(new IllegalArgumentException());
		replay(daoMock);
		
		final Response response = resource.add(newName(), newOwner());
		
		assertThat(response, hasBadRequestStatus());
	}
}
