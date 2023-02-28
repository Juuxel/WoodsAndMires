package juuxel.woodsandmires.data.mixin;

import net.minecraft.data.DataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DataProvider.class)
interface DataProviderMixin {
    @ModifyVariable(method = "writeToPath", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/google/gson/Gson;toJson(Lcom/google/gson/JsonElement;)Ljava/lang/String;", remap = false))
    private static String addNewLine(String content) {
        return content + "\n";
    }
}
