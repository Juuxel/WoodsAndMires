package juuxel.woodsandmires.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueFactory;

import java.util.ArrayList;
import java.util.List;

public final class ConfigBuilder {
    private final Config config;

    private ConfigBuilder(Config config) {
        this.config = config;
    }

    public static ConfigBuilder builder(String description) {
        return new ConfigBuilder(ConfigFactory.empty(description));
    }

    public ConfigBuilder add(String path, Object value, String... comments) {
        ConfigValue configValue = ConfigValueFactory.fromAnyRef(value);
        List<String> commentList = new ArrayList<>(List.of(comments));
        commentList.add("Default: " + configValue.render());
        configValue = configValue.withOrigin(configValue.origin().withComments(commentList));
        Config updated = config.withValue(path, configValue);
        return new ConfigBuilder(updated);
    }

    public Config build() {
        return config;
    }
}
