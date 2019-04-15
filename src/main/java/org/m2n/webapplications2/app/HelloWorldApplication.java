package org.m2n.webapplications2.app;

import org.glassfish.jersey.server.ResourceConfig;

public class HelloWorldApplication extends ResourceConfig {
    public HelloWorldApplication() {
        packages("org.m2n.webapplications2");
    }
}
