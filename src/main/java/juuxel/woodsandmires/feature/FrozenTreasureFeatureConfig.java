package juuxel.woodsandmires.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record FrozenTreasureFeatureConfig(
    BlockStateProvider ice,
    IntProvider height,
    IntProvider radius,
    Identifier lootTable
) implements FeatureConfig {
    public static final Codec<FrozenTreasureFeatureConfig> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("ice").forGetter(FrozenTreasureFeatureConfig::ice),
            IntProvider.POSITIVE_CODEC.fieldOf("height").forGetter(FrozenTreasureFeatureConfig::height),
            IntProvider.POSITIVE_CODEC.fieldOf("radius").forGetter(FrozenTreasureFeatureConfig::radius),
            Identifier.CODEC.fieldOf("loot_table").forGetter(FrozenTreasureFeatureConfig::lootTable)
        ).apply(builder, FrozenTreasureFeatureConfig::new)
    );
}
