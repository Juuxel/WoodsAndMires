package juuxel.woodsandmires.entity;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.item.WamItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum WamBoat {
    PINE(
        "pine",
        () -> WamBlocks.PINE_PLANKS,
        () -> WamItems.PINE_BOAT,
        () -> WamEntityTypes.PINE_BOAT
    );

    private final String name;
    private final Supplier<ItemConvertible> planks;
    private final Supplier<ItemConvertible> boat;
    private final Supplier<EntityType<WamBoatEntity>> entityType;

    WamBoat(String name, Supplier<ItemConvertible> planks, Supplier<ItemConvertible> boat, Supplier<EntityType<WamBoatEntity>> entityType) {
        this.name = name;
        this.planks = planks;
        this.boat = boat;
        this.entityType = entityType;
    }

    public ItemConvertible planks() {
        return planks.get();
    }

    public ItemConvertible boat() {
        return boat.get();
    }

    public EntityType<WamBoatEntity> entityType() {
        return entityType.get();
    }

    public EntityType.EntityFactory<WamBoatEntity> factory() {
        return (type, world) -> new WamBoatEntity(type, world, this);
    }

    public Identifier id() {
        return WoodsAndMires.id(name);
    }
}
