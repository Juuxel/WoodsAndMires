package juuxel.woodsandmires.terrablender;

import juuxel.woodsandmires.WoodsAndMires;
import terrablender.api.BiomeProviders;
import terrablender.api.TerraBlenderApi;

public final class WoodsAndMiresTb implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        BiomeProviders.register(new WamBiomeProvider(WoodsAndMires.id("biomes"), 4));
    }
}
