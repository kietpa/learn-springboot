package com.kiet;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.flywaydb.core.Flyway;

import static org.assertj.core.api.Assertions.assertThat;


// Unit test for DAO layer
// Do not use @SpringBootTest annotation for unit tests
// since it will start unnecessary beans. Use it for integration tests only
public class TestContainersTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue(); // can use debug and breakpoint to try connecting to testcontainer db
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}
