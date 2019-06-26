package org.m2n.webapplications2.exceptions;

import org.m2n.webapplications2.logging.Logging;

public abstract class AppException extends Exception {

    public AppException(String message) {
        super(message);
        Logging.exception(this);
    }

    public AppException(String message, Exception cause) {
        super(message, cause);
        Logging.exception(this);
    }

}
