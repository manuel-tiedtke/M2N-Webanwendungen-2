package org.m2n.webapplications2;

import org.glassfish.jersey.server.ResourceConfig;
import org.m2n.webapplications2.logging.Logging;

public class AppConfig extends ResourceConfig {

    public AppConfig() {
        Logging.debug("[App] <CTOR>");

        packages("org.m2n.webapplications2");
    }

}
