package juuxel.woodsandmires.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksAccessor {
    // Blocks.createLeavesBlock() does the work for me with the settings ;)
    @Invoker
    static LeavesBlock callCreateLeavesBlock(BlockSoundGroup soundGroup) {
        throw new AssertionError("unimplemented invoker");
    }
}
