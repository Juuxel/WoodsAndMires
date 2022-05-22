package juuxel.woodsandmires.block;

import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;

public class WoodVariantBlock extends PillarBlock {
    private final Block main;

    public WoodVariantBlock(Block main, Settings settings) {
        super(settings);
        this.main = main;
    }

    @Override
    public String getTranslationKey() {
        return main.getTranslationKey();
    }
}
