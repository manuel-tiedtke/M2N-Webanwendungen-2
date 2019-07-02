package org.m2n.webapplications2;

import org.glassfish.jersey.server.ResourceConfig;
import org.m2n.webapplications2.logging.Logging;

public class AppConfig extends ResourceConfig {

    public static final String VERSION = "0.1.0-alpha+1";

    public AppConfig() {
        Logging.debug("Spaced Repetition Trainer - Version " + VERSION);
        Logging.debug("[App] <CTOR>");

        packages("org.m2n.webapplications2");
    }

}
