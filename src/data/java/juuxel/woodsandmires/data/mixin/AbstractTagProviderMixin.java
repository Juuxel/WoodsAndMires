package juuxel.woodsandmires.data.mixin;

import net.minecraft.data.server.AbstractTagProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractTagProvider.class)
abstract class AbstractTagProviderMixin {
    @ModifyVariable(method = "method_27046", at = @At(value = "INVOKE_ASSIGN", target = "Lcom/google/gson/Gson;toJson(Lcom/google/gson/JsonElement;)Ljava/lang/String;", remap = false))
    private String addNewLine(String content) {
        return content + "\n";
    }
}
