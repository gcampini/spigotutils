<img src="./Logo.svg" width="318px" alt="Strapi logo" />

#### Finally a complete and useful library for all your Spigot plugins.

## Installation
// TODO expose to Maven

## Goal
The goal is to share with the Spigot's **developpers** community a complete set of utilities to highly speed up the development process and help you build performant and efficient
plugins easily. Tired of coding utils for your own project? You feel like you're reinventing the wheel over and over again? Luckely, we've thought of ~~almost~~ everything.
If you feel like something useful could be added to the library, please feel free to contribute (c.f. the _Contributing_ section below). 

## What is SpiGotUtils exactly?
I'll start by saying what SpiGotUtils is not. **SpiGotUtils is NOT a plugin!** I intentionnaly highly emphasise this point. It is a **library**,
a set of classes and implementations, that you can import in your project as a dependency (e.g. via Gradle or Maven). It is important to always keep that in mind.
To be more specific, because it is a library and not a plugin, here's a non-exhaustive list of what SpiGotUtils can't do:
- Register events Listener on its own. Meaning that an instance of the actual plugin using the library is needed to register the listener.
- Log any message in the console.
- ...

The library will be split in modules, each covering a specific aspect of the game (e.g. Commands, Inventory, World, UI, ...).
I hope you now get a better sense of the technical aspect behind this project.

## What is/will be included?
For now, SpiGotUtils is in construction. Here are some ideas that will certainly be implemented in the future:
- Command utils (autocompletion, typed parameters, errors handling, ...)
- Inventory utils (pagination, locking system, ...)
- Other utils (TPS computing, ...)
And much more. In fact, all you can think of!

## Contributing
You can help me with this project by contributing. All ideas are welcome as long as it follows the purpose and idea of the project.

## License
This project is under **MIT License**.
