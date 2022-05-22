package juuxel.woodsandmires;

import com.google.common.collect.ImmutableList;
import juuxel.woodsandmires.biome.WamBiomes;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.feature.WamConfiguredFeatures;
import juuxel.woodsandmires.feature.WamPlacedFeatures;
import juuxel.woodsandmires.feature.WamFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.SimpleRandomFeatureConfig;
import net.minecraft.world.gen.feature.VegetationConfiguredFeatures;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.function.Supplier;

public final class WoodsAndMires implements ModInitializer {
    public static final String ID = "woods_and_mires";

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        WamBlocks.init();
        WamFeatures.init();
        WamConfiguredFeatures.register();
        WamPlacedFeatures.init();
        WamBiomes.init();

        /* TODO: Fix or rewrite: SimpleRandomFeatureConfig config = VegetationConfiguredFeatures.FOREST_FLOWERS.value().config();
        ImmutableList.Builder<Supplier<PlacedFeature>> builder = ImmutableList.builder();
        builder.addAll(config.features);
        builder.add(() -> Feature.RANDOM_PATCH.configure(
                ConfiguredFeatures.createRandomPatchFeatureConfig(
                    Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(WamBlocks.TANSY)))
                )
            ).withPlacement());
        config.features = builder.build();*/
    }
}
