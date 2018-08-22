package org.testifyproject.demo.greetings.resource;

import static javax.ws.rs.core.Response.Status.ACCEPTED;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Fake;
import org.testifyproject.annotation.Sut;
import org.testifyproject.demo.greetings.GreetingRequest;
import org.testifyproject.demo.greetings.GreetingService;
import org.testifyproject.fixture.GreetingsSystemTest;
import org.testifyproject.junit4.SystemTest;

@GreetingsSystemTest
@RunWith(SystemTest.class)
public class UpdateGreetingResourceST {

    @Sut
    WebTarget sut;

    @Fake
    GreetingService greetingService;

    @Test
    public void givenNoneExistentGreetingUpdateGreetingShouldFakeUpdateAGreeting() {
        //Arrange
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        GreetingRequest request = new GreetingRequest("caio");

        willDoNothing().given(greetingService).updateGreeting(id, request);

        //Act
        Response response = sut
                .path("greetings")
                .path(id.toString())
                .request()
                .put(Entity.json(request));

        //Assert
        assertThat(response.getStatus()).isEqualTo(ACCEPTED.getStatusCode());
        verify(greetingService).updateGreeting(id, request);
    }
}
