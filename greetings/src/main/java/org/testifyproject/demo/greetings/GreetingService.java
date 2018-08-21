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

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service class that contains greetings related business logic.
 *
 * @author saden
 */
@Service
@Transactional
public class GreetingService {

    private final GreetingRepository greetingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    GreetingService(GreetingRepository greetingRepository, ModelMapper modelMapper) {
        this.greetingRepository = greetingRepository;
        this.modelMapper = modelMapper;
    }

    public UUID createGeeting(GreetingRequest request) {
        GreetingEntity entity = modelMapper.map(request, GreetingEntity.class);
        entity = greetingRepository.save(entity);

        return entity.getId();
    }

    public GreetingResponse getGreeting(UUID id) {
        GreetingEntity result = greetingRepository.findOne(id);

        return modelMapper.map(result, GreetingResponse.class);
    }

    public Iterable<GreetingResponse> listGreetings() {
        return StreamSupport.stream(greetingRepository.findAll().spliterator(), false)
                .map(p -> modelMapper.map(p, GreetingResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteGreeting(UUID id) {
        greetingRepository.delete(id);
    }

    public void updateGreeting(UUID id, GreetingRequest request) {
        GreetingEntity entity = greetingRepository.getOne(id);
        modelMapper.map(request, entity);

        GreetingEntity result = greetingRepository.save(entity);
    }
}
