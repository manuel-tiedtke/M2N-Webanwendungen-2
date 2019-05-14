package org.m2n.webapplications2;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {

    public AppConfig() {
        packages("org.m2n.webapplications2");
    }

}
