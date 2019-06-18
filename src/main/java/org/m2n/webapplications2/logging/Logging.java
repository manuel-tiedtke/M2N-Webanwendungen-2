package org.m2n.webapplications2.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {

    private static Logger logger = Logger.getGlobal();

    public static void exception(Exception exception) {
        logger.log(Level.WARNING, "An exception occurred", exception);
        exception.printStackTrace();
    }

    public static void debug(String message) {
        logger.log(Level.FINER, message);
        System.out.println(message);
    }

}
