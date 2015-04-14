package edu.alvin.web.routes;

public class MissingRouteException extends RuntimeException {
    public MissingRouteException(String msg) {
        super(msg);
    }
}
