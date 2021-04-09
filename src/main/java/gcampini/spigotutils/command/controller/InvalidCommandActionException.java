package gcampini.spigotutils.command.controller;

/**
 * @author Gil CAMPINI
 */
public class InvalidCommandActionException extends Exception {

    InvalidCommandActionException(String message) {
        super(message);
    }

    InvalidCommandActionException() {
        this("there was an error while evaluating the command action");
    }

}
