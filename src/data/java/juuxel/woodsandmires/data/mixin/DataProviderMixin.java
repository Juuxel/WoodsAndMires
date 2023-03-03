package juuxel.woodsandmires.data.mixin;

import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Mixin(DataProvider.class)
interface DataProviderMixin {
    @Inject(
        method = "method_46567",
        at = @At(value = "INVOKE", target = "Lcom/google/gson/stream/JsonWriter;close()V", ordinal = 0),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void addNewLine(JsonElement json, DataWriter writer, Path path, CallbackInfo info,
                                   ByteArrayOutputStream out, HashingOutputStream hashing, JsonWriter jsonWriter) throws IOException {
        jsonWriter.flush();
        hashing.write("\n".getBytes(StandardCharsets.UTF_8));
    }
}
