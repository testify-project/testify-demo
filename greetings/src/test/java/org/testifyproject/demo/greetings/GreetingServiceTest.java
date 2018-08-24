package org.testifyproject.demo.greetings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        Class<GreetingDocument> entityType = GreetingDocument.class;
        GreetingDocument entity = mock(entityType);
        String id = "0d216415-1b8e-4ab9-8531-fcbd25d5966f";

        given(modelMapper.map(request, entityType))
            .willReturn(entity);

        given(greetingRepository.save(entity))
            .willReturn(entity);

        given(entity.getId()).willReturn(id);

        //Act
        String result = sut.createGeeting(request);

        //Assert
        assertThat(result).isEqualTo(id);
        verify(modelMapper).map(request, entityType);
        verify(greetingRepository).save(entity);

    }

    //Other test cases left as an exercise
}
