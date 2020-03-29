package es.uvigo.esei.daa.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.dao.PeopleDAOTest;
import es.uvigo.esei.daa.rest.PeopleResourceTest;
import es.uvigo.esei.daa.rest.UsersResourceTest;
import es.uvigo.esei.daa.dao.PetDAOTest;
import es.uvigo.esei.daa.rest.PetResourceTest;

@SuiteClasses({ 
	PeopleDAOTest.class,
	PeopleResourceTest.class,
	UsersResourceTest.class,
	PetDAOTest.class,
	PetResourceTest.class
})
@RunWith(Suite.class)
public class IntegrationTestSuite {
}
