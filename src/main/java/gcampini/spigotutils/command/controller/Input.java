package gcampini.spigotutils.command.controller;

import gcampini.spigotutils.command.argument.InputCommandArgument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method parameter is a command action input.
 *
 * @author Gil CAMPINI
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Input {

    /**
     * Returns the command argument class with which to evaluate the input.
     *
     * @return the command argument class with which to evaluate the input
     */
    Class<? extends InputCommandArgument<?>> argument();

    /**
     * Returns the id of the input argument.
     *
     * @return the id of the input argument
     */
    String id();

}
