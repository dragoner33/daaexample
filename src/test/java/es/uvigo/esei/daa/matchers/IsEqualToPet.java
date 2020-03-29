package es.uvigo.esei.daa.matchers;

import static org.junit.Assert.*;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.daa.entities.Pet;

public class IsEqualToPet extends IsEqualToEntity<Pet> {
	public IsEqualToPet(Pet entity) {
		super(entity);
	}

	@Override
	protected boolean matchesSafely(Pet actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("id", Pet::getId, actual)
				&& checkAttribute("name", Pet::getName, actual)
				&& checkAttribute("owner", Pet::getOwner, actual);
		}
	}

	/**
	 * Factory method that creates a new {@link IsEqualToEntity} matcher with
	 * the provided {@link Pet} as the expected value.
	 * 
	 * @param person the expected person.
	 * @return a new {@link IsEqualToEntity} matcher with the provided
	 * {@link Pet} as the expected value.
	 */
	@Factory
	public static IsEqualToPet equalsToPet(Pet pet) {
		return new IsEqualToPet(pet);
	}
	
	/**
	 * Factory method that returns a new {@link Matcher} that includes several
	 * {@link IsEqualToPerson} matchers, each one using an {@link Person} of the
	 * provided ones as the expected value.
	 * 
	 * @param persons the persons to be used as the expected values.
	 * @return a new {@link Matcher} that includes several
	 * {@link IsEqualToPerson} matchers, each one using an {@link Person} of the
	 * provided ones as the expected value.
	 * @see IsEqualToEntity#containsEntityInAnyOrder(java.util.function.Function, Object...)
	 */
	@Factory
	public static Matcher<Iterable<? extends Pet>> containsPetInAnyOrder(Pet ... pet) {
		return containsEntityInAnyOrder(IsEqualToPet::equalsToPet, pet);
	}

}

