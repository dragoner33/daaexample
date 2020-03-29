package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.Pet;

public class PetDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(PetDAO.class.getName());
	
	/**
	 * Returns a person stored persisted in the system.
	 * 
	 * @param id identifier of the pet.
	 * @return a person with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the person.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted person.
	 */
	public Pet get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pet WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with all the people persisted in the system.
	 * 
	 * @return a list with all the people persisted in the system.
	 * @throws DAOException if an error happens while retrieving the people.
	 */
	public List<Pet> list() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pet";
			
			   //console.log("aqui estamos");
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pet = new LinkedList<>();
					
					while (result.next()) {
						pet.add(rowToEntity(result));
					}
					
					return pet;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Persists a new person in the system. An identifier will be assigned
	 * automatically to the new person.
	 * 
	 * @param name name of the new person. Can't be {@code null}.
	 * @param owner surname of the new person. Can't be {@code null}.
	 * @return a {@link Person} entity representing the persisted person.
	 * @throws DAOException if an error happens while persisting the new person.
	 * @throws IllegalArgumentException if the name or surname are {@code null}.
	 */
	public Pet add(String name, String owner)
	throws DAOException, IllegalArgumentException {
		if (name == null || owner == null) {
			throw new IllegalArgumentException("name and owner can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO pet VALUES(null, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setString(2, owner);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Pet(resultKeys.getInt(1), name, owner);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Modifies a person previously persisted in the system. The person will be
	 * retrieved by the provided id and its current name and surname will be
	 * replaced with the provided.
	 * 
	 * @param pet a {@link Person} entity with the new data.
	 * @throws DAOException if an error happens while modifying the new person.
	 * @throws IllegalArgumentException if the person is {@code null}.
	 */
	public void modify(Pet pet)
	throws DAOException, IllegalArgumentException {
		if (pet == null) {
			throw new IllegalArgumentException("pet can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE pet SET name=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, pet.getName());
				statement.setInt(2, pet.getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("name and owner can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			throw new DAOException();
		}
	}
	
	/**
	 * Removes a persisted person from the system.
	 * 
	 * @param id identifier of the person to be deleted.
	 * @throws DAOException if an error happens while deleting the person.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted person.
	 */
	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM pet WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			throw new DAOException(e);
		}
	}
	
	private Pet rowToEntity(ResultSet row) throws SQLException {
		return new Pet(
			row.getInt("id"),
			row.getString("name"),
			row.getString("owner")
		);
	}
}
