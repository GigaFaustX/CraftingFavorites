package test.mod1.testmod1.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import test.mod1.testmod1.client.RecipeFavorites;

import java.util.ArrayList;
import java.util.List;

@Mixin(RecipeButton.class)
public abstract class RecipeButtonMixin {
    @Inject(method = "extractWidgetRenderState", at = @At("TAIL"))
    private void testmod1$drawFavoriteStar(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta,
                                            CallbackInfo callback) {
        RecipeButton button = (RecipeButton) (Object) this;
        RecipeDisplay display = button.getCollection().getRecipes().stream()
                .filter(entry -> entry.id().equals(button.getCurrentRecipe()))
                .map(entry -> entry.display())
                .findFirst()
                .orElse(null);
        if (display != null && RecipeFavorites.contains(display)) {
            graphics.text(Minecraft.getInstance().font, "\u2605", button.getX() + 17, button.getY(), 0xFFFFD54F, true);
        }
    }

    @Inject(method = "getTooltipText", at = @At("RETURN"), cancellable = true)
    private void testmod1$explainFavorites(ItemStack displayStack, CallbackInfoReturnable<List<Component>> callback) {
        List<Component> tooltip = new ArrayList<>(callback.getReturnValue());
        tooltip.add(Component.literal("Right-click to favorite or unfavorite"));
        callback.setReturnValue(tooltip);
    }
}
