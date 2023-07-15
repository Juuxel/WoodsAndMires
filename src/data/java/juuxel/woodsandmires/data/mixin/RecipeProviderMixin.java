package juuxel.woodsandmires.data.mixin;

import juuxel.woodsandmires.data.builtin.CommonItemTags;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipeProvider.class)
abstract class RecipeProviderMixin {
    @Redirect(
        method = {"createFenceRecipe", "createFenceGateRecipe", "createSignRecipe"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/server/recipe/ShapedRecipeJsonBuilder;input(Ljava/lang/Character;Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/data/server/recipe/ShapedRecipeJsonBuilder;")
    )
    private static ShapedRecipeJsonBuilder replaceStick(ShapedRecipeJsonBuilder builder, Character c, ItemConvertible item) {
        if (item.asItem() == Items.STICK) {
            return builder.input(c, CommonItemTags.WOODEN_RODS);
        }
        return builder.input(c, item);
    }

    @Redirect(
        method = "offerChestBoatRecipe",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/server/recipe/ShapelessRecipeJsonBuilder;input(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/data/server/recipe/ShapelessRecipeJsonBuilder;")
    )
    private static ShapelessRecipeJsonBuilder replaceChest(ShapelessRecipeJsonBuilder builder, ItemConvertible input) {
        if (input.asItem() == Items.CHEST) {
            return builder.input(CommonItemTags.WOODEN_CHESTS);
        }
        return builder.input(input);
    }

    @Redirect(
        method = "offerHangingSignRecipe",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/data/server/recipe/ShapedRecipeJsonBuilder;input(Ljava/lang/Character;Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/data/server/recipe/ShapedRecipeJsonBuilder;")
    )
    private static ShapedRecipeJsonBuilder replaceChain(ShapedRecipeJsonBuilder builder, Character c, ItemConvertible item) {
        if (item.asItem() == Items.CHAIN) {
            return builder.input(c, CommonItemTags.CHAINS);
        }
        return builder.input(c, item);
    }
}
