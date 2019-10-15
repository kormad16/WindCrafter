package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.WindcrafterMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundManager {

    public static final SoundEvent heartContainerSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "heart_container"));
    public static final SoundEvent itemFanfareSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "item_fanfare"));
    public static final SoundEvent heartSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "get_heart"));
    public static final SoundEvent lowHealthSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "low_health"));

}
