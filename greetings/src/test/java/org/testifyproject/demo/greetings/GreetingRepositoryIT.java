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
@VirtualResource(value = "elasticsearch", version = "2.4.6")
@RunWith(IntegrationTest.class)
public class GreetingRepositoryIT {

    @Sut
    GreetingRepository sut;

    @Test
    public void givenGreetingEntitySaveShouldSaveGreetingToTheDatabase() {
        //Arrange
        String phrase = "caio";
        GreetingDocument document = GreetingDocument.builder().phrase(phrase).build();

        //Act
        GreetingDocument result = sut.save(document);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPhrase()).isEqualTo(phrase);

    }
}
