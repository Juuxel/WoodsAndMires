package juuxel.woodsandmires.mixin;

import juuxel.woodsandmires.entity.WamBoatEntity;
import juuxel.woodsandmires.item.WamBoatItem;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BoatItem.class)
abstract class BoatItemMixin {
    @ModifyVariable(
        method = "use",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/BoatEntity;setBoatType(Lnet/minecraft/entity/vehicle/BoatEntity$Type;)V"),
        allow = 1
    )
    private BoatEntity modifyBoat(BoatEntity original) {
        // noinspection ConstantConditions
        if ((Object) this instanceof WamBoatItem wam) {
            return WamBoatEntity.copy(original, wam.getBoatData());
        }

        return original;
    }
}
