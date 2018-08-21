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
package org.testifyproject.demo.greetings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.testifyproject.annotation.Fake;
import org.testifyproject.annotation.Sut;
import org.testifyproject.junit4.UnitTest;

@RunWith(UnitTest.class)
public class GreetingServiceTest {

    @Sut
    GreetingService sut;

    @Fake
    GreetingRepository greetingRepository;

    @Fake
    ModelMapper modelMapper;

    @Test
    public void givenGreetingRequestCreateGreetingShouldSaveGreeting() {
        //Arrange
        GreetingRequest request = mock(GreetingRequest.class);
        Class<GreetingEntity> entityType = GreetingEntity.class;
        GreetingEntity entity = mock(entityType);
        UUID id = UUID.fromString("0d216415-1b8e-4ab9-8531-fcbd25d5966f");

        given(modelMapper.map(request, entityType))
            .willReturn(entity);

        given(greetingRepository.save(entity))
            .willReturn(entity);

        given(entity.getId()).willReturn(id);

        //Act
        UUID result = sut.createGeeting(request);

        //Assert
        assertThat(result).isEqualTo(id);
        verify(modelMapper).map(request, entityType);
        verify(greetingRepository).save(entity);

    }

    //Other test cases left as an exercise
}
