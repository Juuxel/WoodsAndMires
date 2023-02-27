package juuxel.woodsandmires.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public final class WamConfig {
    private static final String BIOME_REGION_WEIGHT_KEY = "generation.biome_region_weight";

    private static final Config DEFAULT = ConfigBuilder.builder("Defaults")
        .add(BIOME_REGION_WEIGHT_KEY, 5,
            "The weight of the biome region.",
            "A higher weight means the region will be more common.")
        .build();

    private static final ConfigRenderOptions CONFIG_RENDER_OPTIONS =
        ConfigRenderOptions.defaults()
            .setOriginComments(false)
            .setJson(false);

    private static boolean loaded = false;
    public static int biomeRegionWeight;

    public static synchronized void load() {
        if (loaded) return;

        try {
            Path configPath = FabricLoader.getInstance().getConfigDir().resolve("woods_and_mires.conf");
            Config config;

            if (Files.exists(configPath)) {
                try (BufferedReader reader = Files.newBufferedReader(configPath)) {
                    config = ConfigFactory.parseReader(reader);
                }
            } else {
                config = ConfigFactory.empty();
            }

            config = config.withFallback(DEFAULT);
            load(config);
            Files.writeString(configPath, config.root().render(CONFIG_RENDER_OPTIONS));
        } catch (Exception e) {
            throw new RuntimeException("Could not load Woods and Mires config!", e);
        }

        loaded = true;
    }

    private static void load(Config config) {
        biomeRegionWeight = config.getInt(BIOME_REGION_WEIGHT_KEY);
    }
}
