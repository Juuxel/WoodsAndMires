package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.entity.WamBoatEntity;
import juuxel.woodsandmires.item.WamBoatDispenserBehavior;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatDispenserBehavior.class)
abstract class BoatDispenserBehaviorMixin {
    @ModifyVariable(
        method = "dispenseSilently",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;setBoatType(Lnet/minecraft/entity/vehicle/BoatEntity$Type;)V"),
        allow = 1
    )
    private BoatEntity modifyBoat(BoatEntity original) {
        // noinspection ConstantConditions
        if ((Object) this instanceof WamBoatDispenserBehavior wam) {
            return WamBoatEntity.copy(original, wam.getBoatData());
        }

        return original;
    }
}
