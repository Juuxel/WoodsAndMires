package juuxel.woodsandmires.entity;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.item.WamItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public enum WamBoat {
    PINE(
        "pine",
        () -> WamBlocks.PINE_PLANKS,
        () -> WamItems.PINE_BOAT,
        () -> WamItems.PINE_CHEST_BOAT,
        () -> WamEntityTypes.PINE_BOAT,
        () -> WamEntityTypes.PINE_CHEST_BOAT
    );

    private final String name;
    private final Supplier<ItemConvertible> planks;
    private final Supplier<ItemConvertible> boat;
    private final Supplier<ItemConvertible> chestBoat;
    private final Supplier<EntityType<BoatEntity>> entityType;
    private final Supplier<EntityType<BoatEntity>> chestEntityType;

    WamBoat(
        String name,
        Supplier<ItemConvertible> planks,
        Supplier<ItemConvertible> boat,
        Supplier<ItemConvertible> chestBoat,
        Supplier<EntityType<BoatEntity>> entityType,
        Supplier<EntityType<BoatEntity>> chestEntityType
    ) {
        this.name = name;
        this.planks = planks;
        this.boat = boat;
        this.chestBoat = chestBoat;
        this.entityType = entityType;
        this.chestEntityType = chestEntityType;
    }

    public ItemConvertible planks() {
        return planks.get();
    }

    public ItemConvertible boat() {
        return boat.get();
    }

    public ItemConvertible chestBoat() {
        return chestBoat.get();
    }

    public EntityType<BoatEntity> entityType(boolean chest) {
        return chest ? chestEntityType.get() : entityType.get();
    }

    public EntityType.EntityFactory<BoatEntity> factory(boolean chest) {
        return (type, world) -> chest
            ? new WamChestBoatEntity(type, world, this)
            : new WamBoatEntity(type, world, this);
    }

    public Identifier id() {
        return WoodsAndMires.id(name);
    }
}
