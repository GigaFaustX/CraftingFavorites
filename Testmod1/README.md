# CraftingFavorites

CraftingFavorites is a client-side Fabric mod that lets you keep frequently used recipes at the top of the crafting recipe book.

## Features

- Right-click a recipe to favorite or unfavorite it.
- Favorited recipes display a gold star and their groups move ahead of other recipes.
- Favorites are saved locally in `config/craftingfavorites-favorites.txt` and do not require server support.

Hover over a recipe to see the right-click hint.

## Compatibility

- Minecraft Java Edition 26.3
- Fabric Loader
- Fabric API
- Java 25 for building from source

## Build

With Java 25 installed, run the Gradle wrapper:

```powershell
.\gradlew.bat build
```

The mod jar is created in `build/libs/`.

## License

All rights reserved. See [LICENSE.txt](LICENSE.txt).
