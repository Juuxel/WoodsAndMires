package juuxel.woodsandmires.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class EncoderBasedDataProvider<T> implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final FabricDataGenerator dataGenerator;
    private final Encoder<T> encoder;
    private final ResourceType resourceType;
    private final String directory;
    private final Function<T, Identifier> idQuery;

    protected EncoderBasedDataProvider(FabricDataGenerator dataGenerator, Encoder<T> encoder, ResourceType resourceType, String directory, Function<T, Identifier> idQuery) {
        this.dataGenerator = dataGenerator;
        this.encoder = encoder;
        this.resourceType = resourceType;
        this.directory = directory;
        this.idQuery = idQuery;
    }

    protected EncoderBasedDataProvider(FabricDataGenerator dataGenerator, Encoder<T> encoder, Registry<T> registry) {
        this(dataGenerator, encoder, ResourceType.SERVER_DATA, registry.getKey().getValue().getPath(), registry::getId);
    }

    protected abstract Stream<T> getEntries();

    @Override
    public void run(DataCache cache) throws IOException {
        DynamicRegistryManager drm = BuiltinRegistries.DYNAMIC_REGISTRY_MANAGER;
        DynamicOps<JsonElement> ops = RegistryOps.of(JsonOps.INSTANCE, drm);

        Iterator<T> iter = getEntries().iterator();
        while (iter.hasNext()) {
            T next = iter.next();
            Identifier id = idQuery.apply(next);
            String dataPath = "%s/%s/%s/%s.json".formatted(resourceType.getDirectory(), id.getNamespace(), directory, id.getPath());
            Path path = dataGenerator.getOutput().resolve(dataPath);
            JsonElement json = encoder.encodeStart(ops, next)
                .getOrThrow(false, msg -> {});
            DataProvider.writeToPath(GSON, cache, json, path);
        }
    }
}
