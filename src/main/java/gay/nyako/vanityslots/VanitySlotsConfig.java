package gay.nyako.vanityslots;

import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;

@Config(id = VanitySlots.MOD_ID)
public final class VanitySlotsConfig {

    @Configurable
    @Configurable.Comment(value = "Mobs React To Vanity", localize = true)
    @Configurable.Synchronized
    public boolean mobsReact = false;
}
