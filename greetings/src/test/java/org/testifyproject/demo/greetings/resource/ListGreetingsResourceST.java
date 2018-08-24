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
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.demo.GreetingApplication;
import org.testifyproject.demo.greetings.GreetingDocument;
import org.testifyproject.fixture.ElasticsearchLocalResource;
import org.testifyproject.fixture.TestConfigHandler;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.SystemTest;

@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@LocalResource(ElasticsearchLocalResource.class)
@ConfigHandler(TestConfigHandler.class)
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
        GenericType<List<GreetingDocument>> genericType
            = new GenericType<List<GreetingDocument>>() {
        };

        List<GreetingDocument> result = response.readEntity(genericType);
        assertThat(result).hasSize(1);
    }

}
