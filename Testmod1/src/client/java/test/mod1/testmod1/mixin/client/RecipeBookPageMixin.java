package test.mod1.testmod1.mixin.client;

import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import test.mod1.testmod1.client.RecipeFavorites;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

@Mixin(RecipeBookPage.class)
public abstract class RecipeBookPageMixin {
    @Shadow abstract void updateCollections(List<RecipeCollection> collections, boolean resetPage, boolean isFiltering);

    @ModifyVariable(method = "updateCollections", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private List<RecipeCollection> testmod1$putFavoriteCollectionsFirst(List<RecipeCollection> collections) {
        return collections.stream()
                .sorted(Comparator.comparing(RecipeBookPageMixin::testmod1$hasFavorite).reversed())
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void testmod1$toggleFavorite(MouseButtonEvent event, int x, int y, int imageWidth, int imageHeight,
                                         boolean doubleClick, CallbackInfoReturnable<Boolean> callback) {
        // SDL's standard right mouse button is 3; left click remains vanilla crafting behavior.
        if (event.button() != 3) {
            return;
        }

        RecipeBookPageAccessor accessor = (RecipeBookPageAccessor) this;
        RecipeButton button = accessor.testmod1$getHoveredButton();
        if (button == null || !button.isMouseOver(event.x(), event.y())) {
            return;
        }

        RecipeDisplay display = button.getCollection().getRecipes().stream()
                .filter(entry -> entry.id().equals(button.getCurrentRecipe()))
                .map(entry -> entry.display())
                .findFirst()
                .orElse(null);
        if (display == null) {
            return;
        }
        RecipeFavorites.toggle(display);
        updateCollections(accessor.testmod1$getRecipeCollections(), true, accessor.testmod1$isFiltering());
        callback.setReturnValue(true);
    }

    private static boolean testmod1$hasFavorite(RecipeCollection collection) {
        return collection.getRecipes().stream().anyMatch(entry -> RecipeFavorites.contains(entry.display()));
    }
}
