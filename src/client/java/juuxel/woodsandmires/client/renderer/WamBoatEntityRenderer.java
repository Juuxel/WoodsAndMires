package juuxel.woodsandmires.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import juuxel.woodsandmires.entity.WamBoat;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Map;

public final class WamBoatEntityRenderer extends BoatEntityRenderer {
    public WamBoatEntityRenderer(EntityRendererFactory.Context context, WamBoat boatData) {
        super(context);
        var id = boatData.id();
        var texture = new Identifier(id.getNamespace(), "textures/entity/boat/" + id.getPath() + ".png");
        var model = new BoatEntityModel(context.getPart(getModelLayer(boatData)));
        texturesAndModels = texturesAndModels.entrySet()
            .stream()
            .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, entry -> Pair.of(texture, model)));
    }

    public static EntityModelLayer getModelLayer(WamBoat boat) {
        var id = boat.id();
        return new EntityModelLayer(new Identifier(id.getNamespace(), "boat/" + id.getPath()), "main");
    }
}
