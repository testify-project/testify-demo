/*
 * Copyright 2016-2017 Testify Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
