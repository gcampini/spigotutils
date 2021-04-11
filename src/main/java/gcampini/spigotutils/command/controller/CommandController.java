package gcampini.spigotutils.command.controller;

import gcampini.spigotutils.command.CommandInputs;
import gcampini.spigotutils.command.CommandSchema;
import gcampini.spigotutils.command.argument.CommandArgument;
import gcampini.spigotutils.command.argument.InputCommandArgument;
import gcampini.spigotutils.command.argument.WellKnownArgument;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gil CAMPINI
 */
public abstract class CommandController {

    private final CommandSchema<?>[] schemas;

    public CommandController() throws InvalidCommandControllerException {
        List<CommandSchema<?>> schemas = new ArrayList<>();
        for (Method method : this.getClass().getMethods()) {
            if (!method.isAnnotationPresent(CommandAction.class)) continue;
            try {
                schemas.add(buildSchema(method));
            } catch (InvalidCommandActionException e) {
                throw new InvalidCommandControllerException(e.getMessage());
            }
        }
        this.schemas = schemas.toArray(new CommandSchema<?>[0]);
    }

    private CommandSchema<?> buildSchema(Method method) throws InvalidCommandActionException {
        CommandAction action = method.getAnnotation(CommandAction.class);

        //if (!Modifier.isStatic(method.getModifiers())) throw new InvalidCommandActionException("method is not static");

        Parameter[] parameters = method.getParameters();
        List<CommandArgument<?>> arguments = new ArrayList<>();

        for (String argumentString : action.value().trim().split(" ")) {
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

        CommandController that = this;
        return new CommandSchema<CommandSender>(action.permission(), arguments.toArray(new CommandArgument<?>[0])) {
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
                    method.invoke(that, parametersValue);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public CommandSchema<?>[] getSchemas() {
        return schemas;
    }

}
