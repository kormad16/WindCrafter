package at.kaindorf.windcrafter.util;

import at.kaindorf.windcrafter.WindcrafterMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class WindcrafterLootTable {

    public static final ResourceLocation ENTITIES_BOKOBLIN = register("enemies/bokoblin");
    public static final ResourceLocation ENTITIES_MOBLIN = register("enemies/moblin");

    private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(WindcrafterMod.MODID, id));
    }

}
