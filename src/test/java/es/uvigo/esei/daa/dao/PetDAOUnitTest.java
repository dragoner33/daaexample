package es.uvigo.esei.daa.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import static es.uvigo.esei.daa.dataset.PetDataset.existentId;
import static es.uvigo.esei.daa.dataset.PetDataset.existentPet;
import static es.uvigo.esei.daa.dataset.PetDataset.newName;
import static es.uvigo.esei.daa.dataset.PetDataset.newPet;
import static es.uvigo.esei.daa.dataset.PetDataset.newOwner;
import static es.uvigo.esei.daa.dataset.PetDataset.pet;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.containsPetInAnyOrder;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.equalsToPet;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Statement;

import es.uvigo.esei.daa.entities.Pet;
import es.uvigo.esei.daa.util.DatabaseQueryUnitTest;

public class PetDAOUnitTest extends DatabaseQueryUnitTest {
	@Test
	public void testList() throws Exception {
		final Pet[] pets = pet();
		
		for (Pet pet : pets) {
			expectPetRow(pet);
		}
		expect(result.next()).andReturn(false);
		result.close();
		
		replayAll();
		final PetDAO petDAO = new PetDAO();

		assertThat(petDAO.list(), containsPetInAnyOrder(pets));
	}
	
	@Test(expected = DAOException.class)
	public void testListUnexpectedException() throws Exception {
		expect(result.next()).andThrow(new SQLException());
		result.close();
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.list();
	}
	
	@Test
	public void testGet() throws Exception {
		final Pet existentPet = existentPet();
		
		expectPetRow(existentPet);
		result.close();
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		
		assertThat(petDAO.get(existentId()), is(equalTo(existentPet)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetMissing() throws Exception {
		expect(result.next()).andReturn(false);
		result.close();
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.get(existentId());
	}
	
	@Test(expected = DAOException.class)
	public void testGetUnexpectedException() throws Exception {
		expect(result.next()).andThrow(new SQLException());
		result.close();
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.get(existentId());
	}

	@Test
	public void testAdd() throws Exception {
		final Pet pet = newPet();
		reset(connection);
		expect(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
			.andReturn(statement);
		expect(statement.executeUpdate()).andReturn(1);
		expect(statement.getGeneratedKeys()).andReturn(result);
		
		// Key retrieval
		expect(result.next()).andReturn(true);
		expect(result.getInt(1)).andReturn(pet.getId());
		connection.close();
		result.close();

		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		final Pet newPet = petDAO.add(pet.getName(), pet.getOwner());
		
		assertThat(newPet, is(equalsToPet(pet)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullName() throws Exception {
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		
		resetAll(); // No expectations
		
		petDAO.add(null, newOwner());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullOwner() throws Exception {
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		
		resetAll(); // No expectations
		
		petDAO.add(newName(), null);
	}

	@Test(expected = DAOException.class)
	public void testAddZeroUpdatedRows() throws Exception {
		reset(connection);
		expect(connection.prepareStatement(anyString(), eq(1)))
			.andReturn(statement);
		expect(statement.executeUpdate()).andReturn(0);
		connection.close();

		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.add(newName(), newOwner());
	}

	@Test(expected = DAOException.class)
	public void testAddNoGeneratedKey() throws Exception {
		reset(connection);
		expect(connection.prepareStatement(anyString(), eq(1)))
			.andReturn(statement);
		expect(statement.executeUpdate()).andReturn(1);
		expect(statement.getGeneratedKeys()).andReturn(result);
		expect(result.next()).andReturn(false);
		result.close();
		connection.close();

		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.add(newName(), newOwner());
	}
	
	@Test(expected = DAOException.class)
	public void testAddUnexpectedException() throws Exception {
		reset(connection);
		expect(connection.prepareStatement(anyString(), eq(1)))
			.andReturn(statement);
		expect(statement.executeUpdate()).andThrow(new SQLException());
		connection.close();
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.add(newName(), newOwner());
	}

	@Test
	public void testDelete() throws Exception {
		expect(statement.executeUpdate()).andReturn(1);
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.delete(existentId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteInvalidId() throws Exception {
		expect(statement.executeUpdate()).andReturn(0);
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.delete(existentId());
	}

	@Test(expected = DAOException.class)
	public void testDeleteUnexpectedException() throws Exception {
		expect(statement.executeUpdate()).andThrow(new SQLException());
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.delete(existentId());
	}

	@Test
	public void testModify() throws Exception {
		expect(statement.executeUpdate()).andReturn(1);

		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.modify(existentPet());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullPet() throws Exception {
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		
		resetAll(); // No expectations
		
		petDAO.modify(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyZeroUpdatedRows() throws Exception {
		expect(statement.executeUpdate()).andReturn(0);

		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.modify(existentPet());
	}
	
	@Test(expected = DAOException.class)
	public void testModifyUnexpectedException() throws Exception {
		expect(statement.executeUpdate()).andThrow(new SQLException());
		
		replayAll();
		
		final PetDAO petDAO = new PetDAO();
		petDAO.modify(existentPet());
	}
	
	private void expectPetRow(Pet pet) throws SQLException {
		expect(result.next()).andReturn(true);
		expect(result.getInt("id")).andReturn(pet.getId());
		expect(result.getString("name")).andReturn(pet.getName());
		expect(result.getString("owner")).andReturn(pet.getOwner());
	}
}
