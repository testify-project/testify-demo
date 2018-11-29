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

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.testifyproject.demo.greetings.GreetingRequest;
import org.testifyproject.demo.greetings.GreetingService;

/**
 * A resource that creates a new greeting.
 *
 * @author saden
 */
@RestController
public class CreateGreetingResource {

    private final GreetingService greetingService;

    @Autowired
    CreateGreetingResource(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @RequestMapping(
            path = "/greetings",
            method = RequestMethod.POST,
            consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE
            })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createGreeting(@Valid @RequestBody GreetingRequest request) {
        String id = greetingService.createGeeting(request);

        ControllerLinkBuilder link = linkTo(CreateGreetingResource.class)
                .slash("greetings")
                .slash(id);

        return ResponseEntity.created(link.toUri()).build();
    }

}
