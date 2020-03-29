package es.uvigo.esei.daa.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PetUnitTest {
	@Test
	public void testPetIntStringString() {
		final int id = 1;
		final String name = "John";
		final String owner = "5";
		
		final Person pet = new Person(id, name, owner);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSurname(), is(equalTo(owner)));
	}

	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringNullName() {
		new Pet(1, null, "Doe");
	}
	
	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringNullOwner() {
		new Pet(1, "John", null);
	}

	@Test
	public void testSetName() {
		final int id = 1;
		final String owner = "Doe";
		
		final Pet pet = new Pet(id, "John", owner);
		pet.setName("Juan");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo("Juan")));
		assertThat(pet.getOwner(), is(equalTo(owner)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullName() {
		final Pet pet = new Pet(1, "John", "5");
		
		pet.setName(null);
	}

	@Test
	public void testSetOwner() {
		final int id = 1;
		final String name = "John";
		
		final Pet pet = new Pet(id, name, "5");
		pet.setOwner("Dolores");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getOwner(), is(equalTo("Dolores")));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullOwner() {
		final Pet pet = new Pet(1, "John", "5");
		
		pet.setOwner(null);
	}

	@Test
	public void testEqualsObject() {
		final Pet petA = new Pet(1, "Name A", "Surname A");
		final Pet petB = new Pet(1, "Name B", "Surname B");
		
		assertTrue(petA.equals(petB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Pet.class)
			.withIgnoredFields("name", "owner")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
}

