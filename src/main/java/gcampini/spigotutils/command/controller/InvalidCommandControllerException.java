package gcampini.spigotutils.command.controller;

/**
 * @author Gil CAMPINI
 */
public class InvalidCommandControllerException extends Exception {

    InvalidCommandControllerException(String message) {
        super(message);
    }

    InvalidCommandControllerException() {
        this("There was an error while evaluating the command controller class");
    }

}
