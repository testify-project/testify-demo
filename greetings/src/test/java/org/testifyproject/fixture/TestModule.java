package org.testifyproject.fixture;

import java.net.InetAddress;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestModule {

    @Primary
    @Bean(destroyMethod = "close")
    public Client testClient(
            @Qualifier("resource:/elasticsearch:2.4.6/resource") InetAddress inetAddress)
            throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch").build();

        TransportClient client
                = TransportClient.builder()
                        .settings(settings)
                        .build()
                        .addTransportAddress(new InetSocketTransportAddress(inetAddress, 9300));

        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("greeting", "greeting", "1")
                .setSource("{\"phrase\":\"hello\"}");

        indexRequestBuilder.execute().get();

        return client;
    }

}
