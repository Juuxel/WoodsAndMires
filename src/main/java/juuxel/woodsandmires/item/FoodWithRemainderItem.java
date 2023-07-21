package juuxel.woodsandmires.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FoodWithRemainderItem extends Item {
    public FoodWithRemainderItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        var remainder = stack.getRecipeRemainder();
        super.finishUsing(stack, world, user);

        // If the slot is empty, we can safely insert the empty bottle there.
        if (stack.isEmpty()) {
            return remainder;
        }

        // Otherwise, we have to insert it to the inventory if not in creative mode.
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            player.getInventory().offerOrDrop(remainder);
        }

        // Return the leftover food.
        return stack;
    }
}
