package juuxel.woodsandmires.data.mixin;

import juuxel.woodsandmires.data.builtin.CommonItemTags;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RecipeProvider.class)
abstract class RecipeProviderMixin {
    @ModifyVariable(
        method = {"saveRecipe", "saveRecipeAdvancement"},
        at = @At(value = "INVOKE_ASSIGN", target = "Lcom/google/gson/Gson;toJson(Lcom/google/gson/JsonElement;)Ljava/lang/String;", remap = false),
        ordinal = 0
    )
    private static String addNewLine(String json) {
        return json + "\n";
    }

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
}
