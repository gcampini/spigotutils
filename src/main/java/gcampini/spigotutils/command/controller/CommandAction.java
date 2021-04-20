package gcampini.spigotutils.command.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method should be interpreted as a command action
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAction {

    /**
     * Returns the command route.
     * Example: health set <amount>
     *
     * @return the command route
     */
    String value();

    /**
     * Returns the permission needed to execute the command.
     *
     * @return the permission needed to execute the command
     */
    String permission() default "";

    /**
     * Returns the description of the command.
     *
     * @return the description of the command
     */
    String description() default "";

}