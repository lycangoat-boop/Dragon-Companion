# Dragon Companion

Minecraft Java Edition **1.20.1**, Minecraft Forge **47.3.1**, Java **17**.

## Current foundation
- ForgeGradle project structure.
- Registered dragon entity type and dragon whistle item.
- Whistle summons a tamed dragon or calls your existing dragon closer.
- Spanish and English display names.

## Build
Install a Java 17 JDK and run from the project directory:

- Windows: `gradlew.bat build`
- Linux/macOS: `./gradlew build`

The compiled mod jar will be in `build/libs/`.

## Development roadmap
1. Add the dragon's custom model, renderer, and textures.
2. Implement owner-following, sitting, and protection behavior.
3. Add rideable flight controls and landing.
4. Add a fire-breath attack with particles, cooldown, and owner-safe damage.

This is the initial code foundation; flight, fire breath, and the custom dragon visuals are not implemented yet.
