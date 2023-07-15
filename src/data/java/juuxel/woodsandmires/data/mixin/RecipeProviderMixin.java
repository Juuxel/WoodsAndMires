package juuxel.woodsandmires.data.mixin;

import net.minecraft.data.server.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
}
