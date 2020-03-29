package es.uvigo.esei.daa.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.dao.PeopleDAOUnitTest;
import es.uvigo.esei.daa.entities.PersonUnitTest;
import es.uvigo.esei.daa.rest.PeopleResourceUnitTest;
import es.uvigo.esei.daa.dao.PetDAOUnitTest;
import es.uvigo.esei.daa.entities.PetUnitTest;
import es.uvigo.esei.daa.rest.PetResourceUnitTest;

@SuiteClasses({
	PersonUnitTest.class,
	PeopleDAOUnitTest.class,
	PeopleResourceUnitTest.class,
	PetUnitTest.class,
	PetDAOUnitTest.class,
	PetResourceUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
