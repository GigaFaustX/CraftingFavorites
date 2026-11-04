package test.mod1.testmod1.mixin.client;

import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RecipeBookPage.class)
public interface RecipeBookPageAccessor {
    @Accessor("hoveredButton")
    RecipeButton testmod1$getHoveredButton();

    @Accessor("recipeCollections")
    List<RecipeCollection> testmod1$getRecipeCollections();

    @Accessor("isFiltering")
    boolean testmod1$isFiltering();
}
