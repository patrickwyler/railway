package ch.teko.railway.integrationtests;

import ch.teko.railway.repositories.StationRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;


@Getter(AccessLevel.PROTECTED)
@RunWith(SpringRunner.class)
@DataJpaTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationRepositoryIntegrationTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StationRepository repository;

    @Test
    public void testExistsRootStation_noRootStationCreatedYet() {
        // Act
        final boolean result = getRepository().existsRootStation();

        // Assert
        assertFalse(result);
    }
}
