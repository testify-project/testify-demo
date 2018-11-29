package org.testifyproject.demo.greetings.resource;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Sut;
import org.testifyproject.fixture.GreetingsSystemTest;
import org.testifyproject.junit4.SystemTest;

@GreetingsSystemTest
@RunWith(SystemTest.class)
public class RemoveGreetingResourceST {

    @Sut
    WebTarget sut;

    @Test
    public void givenGreetingIdDeleteGreetingShouldDeleteTheGreeting() {
        //Act
        Response response = sut
                .path("greetings")
                .path("1")
                .request()
                .delete();

        //Assert
        assertThat(response.getStatus()).isEqualTo(NO_CONTENT.getStatusCode());
        assertThat(response.hasEntity()).isFalse();
    }

}
