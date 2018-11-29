package org.testifyproject.demo.greetings.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.ClientInstance;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingApplication;
import org.testifyproject.demo.greetings.GreetingResponse;
import org.testifyproject.fixture.TestConfigHandler;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.SystemTest;

@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@ConfigHandler(TestConfigHandler.class)
@VirtualResource(value = "elasticsearch", version = "2.4.6")
@RunWith(SystemTest.class)
public class GetGreetingResourceST {

    @Sut
    ClientInstance<WebTarget, Client> sut;

    @Test
    public void givenGreetingIdGetGreetingShouldReturnTheGreeting() {
        //Act
        Response response = sut.getClient().getValue()
                .path("greetings")
                .path("1")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());

        GreetingResponse result = response.readEntity(GreetingResponse.class);
        assertThat(result).isNotNull();
    }
}
