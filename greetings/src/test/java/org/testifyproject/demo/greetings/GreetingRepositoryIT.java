/*
 * Copyright 2018 saden.
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.Sut;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingModule;
import org.testifyproject.fixture.TestModule;
import org.testifyproject.junit4.IntegrationTest;

/**
 *
 * @author saden
 */
@Module(GreetingModule.class)
@Module(value = TestModule.class, test = true)
@VirtualResource(value = "postgres", version = "9.4")
@RunWith(IntegrationTest.class)
public class GreetingRepositoryIT {

    @Sut
    GreetingRepository sut;

    @Test
    public void givenGreetingEntitySaveShouldSaveGreetingToTheDatabase() {
        //Arrange
        String phrase = "caio";
        GreetingEntity entity = new GreetingEntity(phrase);

        //Act
        GreetingEntity result = sut.save(entity);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getPhrase()).isEqualTo(phrase);

    }
}
