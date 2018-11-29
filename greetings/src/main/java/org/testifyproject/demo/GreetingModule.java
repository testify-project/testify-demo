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
package org.testifyproject.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.modelmapper.ModelMapper;
import static org.modelmapper.config.Configuration.AccessLevel.PUBLIC;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Greeting Spring Java Config class..
 *
 * @author saden
 */
@EnableAutoConfiguration(exclude = {
    ElasticsearchAutoConfiguration.class,
    ElasticsearchDataAutoConfiguration.class,
    ElasticsearchRepositoriesAutoConfiguration.class})
@Configuration
@EnableElasticsearchRepositories("org.testifyproject.demo")
@ComponentScan("org.testifyproject.demo")
public class GreetingModule {

    @Bean
    public Client client() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "productionElasticsearch").build();

        InetAddress address = InetAddress.getByName("production.host");

        return TransportClient.builder()
                .settings(settings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(address, 9300));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        org.modelmapper.config.Configuration configuration = mapper.getConfiguration();
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        configuration.setFieldAccessLevel(PUBLIC);
        configuration.setMethodAccessLevel(PUBLIC);
        configuration.setAmbiguityIgnored(false);
        configuration.setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        configuration.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

        return mapper;
    }
}
