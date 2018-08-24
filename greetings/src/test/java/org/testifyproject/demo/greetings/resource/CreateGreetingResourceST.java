package org.testifyproject.demo.greetings.resource;

import static javax.ws.rs.core.Response.Status.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.ClientInstance;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingApplication;
import org.testifyproject.demo.greetings.GreetingDocument;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.SystemTest;

@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@VirtualResource(value = "elasticsearch", version = "2.4.6")
@RunWith(SystemTest.class)
public class CreateGreetingResourceST {

    @Sut
    ClientInstance<WebTarget, Client> sut;

    @ConfigHandler
    void configureClient(ClientBuilder clientBuilder) {
        clientBuilder.register(JacksonFeature.class);
    }

    @Test
    public void givenGreetingRequestPostGreetingShouldCreateGreeting() {
        //Arrange
        GreetingDocument entity = new GreetingDocument("caio");

        //Act
        Response response = sut.getClient().getValue()
            .path("greetings")
            .request()
            .post(Entity.json(entity));

        //Assert
        assertThat(response.getStatus()).isEqualTo(CREATED.getStatusCode());
        assertThat(response.getLocation()).isNotNull();
    }
}
