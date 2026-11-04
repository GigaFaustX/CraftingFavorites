package test.mod1.testmod1.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/** Stores recipe favorites locally, without changing recipe-book data on a server. */
public final class RecipeFavorites {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("craftingfavorites-favorites.txt");
    private static final Set<String> FAVORITES = new LinkedHashSet<>();

    static {
        try {
            if (Files.exists(FILE)) {
                FAVORITES.addAll(Files.readAllLines(FILE, StandardCharsets.UTF_8));
            }
        } catch (IOException exception) {
            System.err.println("[Testmod1] Could not load recipe favorites: " + exception.getMessage());
        }
    }

    private RecipeFavorites() {
    }

    public static boolean contains(RecipeDisplay display) {
        return FAVORITES.contains(display.toString());
    }

    public static boolean toggle(RecipeDisplay display) {
        String key = display.toString();
        boolean added;
        if (FAVORITES.remove(key)) {
            added = false;
        } else {
            FAVORITES.add(key);
            added = true;
        }
        save();
        return added;
    }

    private static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Files.write(FILE, FAVORITES, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.err.println("[Testmod1] Could not save recipe favorites: " + exception.getMessage());
        }
    }
}
