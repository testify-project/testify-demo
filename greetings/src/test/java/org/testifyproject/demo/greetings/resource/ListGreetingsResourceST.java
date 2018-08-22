package org.testifyproject.demo.greetings.resource;

import static javax.ws.rs.core.Response.Status.OK;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingApplication;
import org.testifyproject.demo.greetings.GreetingEntity;
import org.testifyproject.fixture.TestConfigHandler;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.SystemTest;

@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@ConfigHandler(TestConfigHandler.class)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(SystemTest.class)
public class ListGreetingsResourceST {

    @Sut
    WebTarget sut;

    @Test
    public void callToListGreetingsShouldReturnAListOfGreetings() {
        //Act
        Response response = sut
                .path("greetings")
                .request()
                .get();

        //Assert
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        GenericType<List<GreetingEntity>> genericType =
                new GenericType<List<GreetingEntity>>() {
        };

        List<GreetingEntity> result = response.readEntity(genericType);
        assertThat(result).hasSize(1);
    }

}
