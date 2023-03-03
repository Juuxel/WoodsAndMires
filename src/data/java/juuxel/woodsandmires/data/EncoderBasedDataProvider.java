package juuxel.woodsandmires.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public abstract class EncoderBasedDataProvider<T> extends FabricDynamicRegistryProvider {
    private final RegistryKey<? extends Registry<T>> registryKey;

    protected EncoderBasedDataProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, RegistryKey<? extends Registry<T>> registryKey) {
        super(output, registriesFuture);
        this.registryKey = registryKey;
    }

    protected abstract Stream<RegistryKey<T>> getEntries();

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        var wrapper = registries.getWrapperOrThrow(registryKey);
        getEntries().forEach(key -> entries.add(wrapper, key));
    }
}
