package es.uvigo.esei.daa.dataset;

import static org.junit.Assert.*;

import org.junit.Test;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.function.Predicate;

import es.uvigo.esei.daa.entities.Pet;

public final class PetDataset {
	private PetDataset() {}
	
	public static Pet[] pet() {
		return new Pet[] {
			new Pet(1, "Antón", "1"),
			new Pet(2, "Ana", "2"),
			new Pet(3, "Manuel", "3"),
			new Pet(4, "María", "4"),
			new Pet(5, "Lorenzo", "5"),
			new Pet(6, "Laura", "6"),
			new Pet(7, "Perico", "7"),
			new Pet(8, "Patricia", "8"),
			new Pet(9, "Julia", "9"),
			new Pet(10, "Juan", "10")
		};
	}
	
	public static Pet[] petWithout(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Pet> hasValidId = pet ->
			binarySearch(ids, pet.getId()) < 0;
		
		return stream(pet())
			.filter(hasValidId)
		.toArray(Pet[]::new);
	}
	
	public static Pet pet(int id) {
		return stream(pet())
			.filter(pet -> pet.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 5;
	}
	
	public static int nonExistentId() {
		return 1234;
	}

	public static Pet existentPet() {
		return pet(existentId());
	}
	
	public static Pet nonExistentPet() {
		return new Pet(nonExistentId(), "Jane", "13");
	}
	
	public static String newName() {
		return "John";
	}
	
	public static String newOwner() {
		return "5";
	}
	
	public static Pet newPet() {
		return new Pet(pet().length + 1, newName(), newOwner());
	}
}
