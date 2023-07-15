package juuxel.woodsandmires.data;

import juuxel.woodsandmires.WoodsAndMires;
import juuxel.woodsandmires.block.WamBlocks;
import juuxel.woodsandmires.item.WamItemTags;
import juuxel.woodsandmires.item.WamItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class WamRecipeProvider extends FabricRecipeProvider {
    public static final BlockFamily PINE_FAMILY = BlockFamilies.register(WamBlocks.PINE_PLANKS)
        .button(WamBlocks.PINE_BUTTON)
        .door(WamBlocks.PINE_DOOR)
        .fence(WamBlocks.PINE_FENCE)
        .fenceGate(WamBlocks.PINE_FENCE_GATE)
        .pressurePlate(WamBlocks.PINE_PRESSURE_PLATE)
        .sign(WamBlocks.PINE_SIGN, WamBlocks.PINE_WALL_SIGN.get())
        .slab(WamBlocks.PINE_SLAB)
        .stairs(WamBlocks.PINE_STAIRS)
        .trapdoor(WamBlocks.PINE_TRAPDOOR)
        .group("wooden")
        .unlockCriterionName("has_planks")
        .build();

    public WamRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // Wooden
        generateFamily(exporter, PINE_FAMILY);
        offerPlanksRecipe(exporter, WamBlocks.PINE_PLANKS, WamItemTags.THICK_PINE_LOGS, 4);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, WamBlocks.PINE_PLANKS, 2)
            .input(WamBlocks.PINE_SHRUB_LOG)
            .group("planks")
            .criterion("has_log", conditionsFromItem(WamBlocks.PINE_SHRUB_LOG))
            .offerTo(exporter, WoodsAndMires.id("pine_planks_from_shrub_log"));
        offerBarkBlockRecipe(exporter, WamBlocks.PINE_WOOD, WamBlocks.PINE_LOG);
        offerBarkBlockRecipe(exporter, WamBlocks.AGED_PINE_WOOD, WamBlocks.AGED_PINE_LOG);
        offerBarkBlockRecipe(exporter, WamBlocks.PINE_SNAG_WOOD, WamBlocks.PINE_SNAG_LOG);
        offerBarkBlockRecipe(exporter, WamBlocks.STRIPPED_PINE_WOOD, WamBlocks.STRIPPED_PINE_LOG);
        offerBoatRecipe(exporter, WamItems.PINE_BOAT, WamBlocks.PINE_PLANKS);
        offerChestBoatRecipe(exporter, WamItems.PINE_CHEST_BOAT, WamItems.PINE_BOAT);

        // Dyes
        offerShapelessRecipe(exporter, Items.MAGENTA_DYE, WamBlocks.FIREWEED, "magenta_dye", 2);
        offerShapelessRecipe(exporter, Items.PINK_DYE, WamBlocks.HEATHER, "pink_dye", 1);
        offerShapelessRecipe(exporter, Items.YELLOW_DYE, WamBlocks.TANSY, "yellow_dye", 1);
    }

    public static void offerShapelessRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
            .input(input)
            .group(group)
            .criterion(hasItem(input), conditionsFromItem(input))
            .offerTo(exporter, WoodsAndMires.id(convertBetween(output, input)));
    }
}
