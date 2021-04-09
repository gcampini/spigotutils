package gcampini.spigotutils.command.controller;

import gcampini.spigotutils.command.CommandHandler;
import gcampini.spigotutils.command.CommandInputs;
import gcampini.spigotutils.command.CommandSchema;
import gcampini.spigotutils.command.argument.CommandArgument;
import gcampini.spigotutils.command.argument.InputCommandArgument;
import gcampini.spigotutils.command.argument.WellKnownArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gil CAMPINI
 */
public class CommandControllerManager {

    public static CommandHandler buildCommandHandler(PluginCommand command, Class<?> controllerClass) throws InvalidCommandControllerException {
        List<CommandSchema<?>> schemas = new ArrayList<>();
        for (Method method : controllerClass.getMethods()) {
            if (!method.isAnnotationPresent(CommandAction.class)) continue;
            try {
                //System.out.println(Arrays.toString(buildSchema(method).getArguments()));
                schemas.add(buildSchema(method));
            } catch (InvalidCommandActionException e) {
                throw new InvalidCommandControllerException(e.getMessage());
            }
        }
        return new CommandHandler(command, schemas.toArray(new CommandSchema<?>[0]));
    }

    private static CommandSchema<?> buildSchema(Method method) throws InvalidCommandActionException {
        Objects.requireNonNull(method, "method is null");
        CommandAction c = method.getAnnotation(CommandAction.class);
        if (c == null) throw new IllegalArgumentException("method is not annotated with @Command");

        // Method is necessarily public because we can see it
        if (!Modifier.isPublic(method.getModifiers())) throw new InvalidCommandActionException("method is not public");
        if (!Modifier.isStatic(method.getModifiers())) throw new InvalidCommandActionException("method is not static");

        Parameter[] parameters = method.getParameters();
        List<CommandArgument<?>> arguments = new ArrayList<>();

        for (String argumentString : c.value().trim().split(" ")) {
            argumentString = argumentString.trim();
            if (argumentString.isEmpty()) continue;
            CommandArgument<?> argument = null;

            if (argumentString.startsWith("<") && argumentString.endsWith(">")) {
                // Input argument
                argumentString = argumentString.substring(1, argumentString.length() - 1).trim();
                if (argumentString.isEmpty()) throw new InvalidCommandActionException("invalid input argument id");
                for (Parameter parameter : parameters) {
                    if (parameter.isAnnotationPresent(Sender.class) || !parameter.isAnnotationPresent(Input.class))
                        continue;
                    Input input = parameter.getAnnotation(Input.class);
                    String id = input.id();
                    if (!id.equals(argumentString)) continue;
                    Class<? extends InputCommandArgument<?>> argumentClass = input.argument();
                    Constructor<? extends InputCommandArgument<?>> constructor;
                    try {
                        constructor = argumentClass.getConstructor(String.class);
                        argument = constructor.newInstance(argumentString);
                    } catch (NoSuchMethodException e) {
                        throw new InvalidCommandActionException(
                                argumentClass.getSimpleName() +
                                        " does not have a constructor matching this signature: public " +
                                        argumentClass.getSimpleName() +
                                        "(String id) { ... }");
                    } catch (IllegalAccessException e) {
                        throw new InvalidCommandActionException("cannot access constructor of " + argumentClass.getSimpleName());
                    } catch (InstantiationException e) {
                        throw new InvalidCommandActionException(argumentClass.getSimpleName() + " is not instantiable");
                    } catch (InvocationTargetException e) {
                        throw new InvalidCommandActionException("an error has occurred while instantiating " + argumentClass.getSimpleName());
                    }
                }
            } else {
                // Well known argument
                String[] aliases = argumentString.split("\\|");
                String[] others = new String[aliases.length - 1];
                System.arraycopy(aliases, 1, others, 0, others.length);
                argument = new WellKnownArgument(aliases[0], others);
            }

            if (argument == null) {
                throw new InvalidCommandActionException("missing @Input(id=\"" + argumentString + "\", ...) annotated parameter in method " + method.getName());
            } else arguments.add(argument);
        }

        return new CommandSchema<>(c.permission(), arguments.toArray(new CommandArgument<?>[0])) {
            @Override
            public void execute(CommandSender sender, CommandInputs inputs) {
                Object[] parametersValue = new Object[parameters.length];
                for (int i = 0; i < parametersValue.length; i++) {
                    Parameter parameter = parameters[i];
                    if (parameter.isAnnotationPresent(Sender.class)) parametersValue[i] = sender;
                    else {
                        Input input = parameter.getAnnotation(Input.class);
                        if (input == null) continue;
                        parametersValue[i] = inputs.get(input.id());
                    }
                }
                try {
                    method.invoke(null, parametersValue);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
