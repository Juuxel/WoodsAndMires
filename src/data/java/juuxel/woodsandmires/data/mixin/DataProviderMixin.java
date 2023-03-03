package juuxel.woodsandmires.data.mixin;

import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

@Mixin(DataProvider.class)
interface DataProviderMixin {
    @Inject(
        method = "writeToPath",
        at = @At(value = "INVOKE", target = "Lcom/google/gson/stream/JsonWriter;close()V", remap = false),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void addNewLine(DataWriter writer, JsonElement json, Path path, CallbackInfo info,
                                   ByteArrayOutputStream out, HashingOutputStream hashing, Writer w) throws IOException {
        w.write('\n');
    }
}
