package org.testifyproject.fixture;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.testifyproject.LocalResourceInstance;
import org.testifyproject.LocalResourceProvider;
import org.testifyproject.TestContext;
import org.testifyproject.annotation.LocalResource;
import org.testifyproject.core.LocalResourceInstanceBuilder;
import org.testifyproject.core.util.FileSystemUtil;
import org.testifyproject.trait.PropertiesReader;

public class ElasticsearchLocalResource implements
        LocalResourceProvider<Settings.Builder, Node, Client> {

    private final FileSystemUtil fileSystemUtil = FileSystemUtil.INSTANCE;

    private Node node;
    private Client client;

    @Override
    public Settings.Builder configure(TestContext testContext, LocalResource localResource,
            PropertiesReader configReader) {
        String testName = testContext.getName();
        String pathHome = fileSystemUtil.createPath("target", "elasticsearch", testName);

        return Settings.builder()
                .put("node.name", testContext.getName())
                .put("path.home", pathHome);
    }

    @Override
    public LocalResourceInstance<Node, Client> start(TestContext testContext,
            LocalResource localResource,
            Settings.Builder config) throws Exception {
        String pathHome = config.get("path.home");
        fileSystemUtil.recreateDirectory(pathHome);

        node = NodeBuilder.nodeBuilder()
                .settings(config)
                .clusterName(testContext.getName())
                .data(true)
                .local(true)
                .node();

        node.start();
        client = node.client();

        //Load Test Data
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("greeting", "greeting")
                .setSource("{\"id\":\"0d216415-1b8e-4ab9-8531-fcbd25d5966f\", \"phrase\":\"hello\"}");

        indexRequestBuilder.get();

        return LocalResourceInstanceBuilder.builder()
                .resource(node)
                .client(client, Client.class)
                .build("elasticsearch2", localResource);
    }

    @Override
    public void stop(TestContext testContext, LocalResource localResource,
            LocalResourceInstance<Node, Client> instance)
            throws Exception {
        client.close();
        node.close();
    }

}
