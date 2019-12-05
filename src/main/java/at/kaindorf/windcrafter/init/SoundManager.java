package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.WindcrafterMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundManager {

    public static final SoundEvent heartContainerSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "heart_container"));
    public static final SoundEvent itemFanfareSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "item_fanfare"));
    public static final SoundEvent heartSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "get_heart"));
    public static final SoundEvent lowHealthSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "low_health"));
    public static final SoundEvent smallPickupSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "small_pickup"));
    public static final SoundEvent errorSoundEvent = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "zelda_error"));

    // Entities
    // Mobs
    // Bokoblin
    public static final SoundEvent bokoblinAmbient = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.bokoblin.ambient"));
    public static final SoundEvent bokoblinHurt = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.bokoblin.hurt"));
    public static final SoundEvent bokoblinDeath = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.bokoblin.death"));
    // Moblin
    public static final SoundEvent moblinAmbient = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.moblin.ambient"));
    public static final SoundEvent moblinnHurt = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.moblin.hurt"));
    public static final SoundEvent moblinDeath = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.moblin.death"));
    // ChuChu
    public static final SoundEvent chuChuAmbient = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.chuchu.ambient"));
    public static final SoundEvent chuChuHurt = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.chuchu.hurt"));
    public static final SoundEvent chuChuDeath = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.chuchu.death"));
    public static final SoundEvent chuChuSquish = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.chuchu.squish"));
    public static final SoundEvent chuChuJump = new SoundEvent(new ResourceLocation(WindcrafterMod.MODID, "entity.windcrafter.chuchu.jump"));
    public static final SoundEvent chuChuFall = chuChuSquish;

}
