package org.testifyproject.demo.greetings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingModule;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.IntegrationTest;

@Module(GreetingModule.class)
@Module(value = TestModule.class, test = true)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(IntegrationTest.class)
public class GreetingRepositoryIT {

    @Sut
    GreetingRepository sut;

    @Test
    public void givenGreetingEntitySaveShouldSaveGreetingToTheDatabase() {
        //Arrange
        String phrase = "caio";
        GreetingEntity entity = new GreetingEntity(phrase);

        //Act
        GreetingEntity result = sut.save(entity);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPhrase()).isEqualTo(phrase);

    }
}
