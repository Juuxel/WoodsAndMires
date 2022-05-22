package juuxel.woodsandmires.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class GroundLogBlock extends PillarBlock {
    public static final BooleanProperty MID = BooleanProperty.of("mid");
    private final Block main;

    public GroundLogBlock(Block main, Settings settings) {
        super(settings);
        this.main = main;
        setDefaultState(getDefaultState().with(MID, false));
    }

    @Override
    public String getTranslationKey() {
        return main.getTranslationKey();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(MID);
    }
}
