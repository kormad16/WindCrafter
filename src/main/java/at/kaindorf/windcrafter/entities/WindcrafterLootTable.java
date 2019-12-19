package at.kaindorf.windcrafter.entities;

import at.kaindorf.windcrafter.WindcrafterMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class WindcrafterLootTable {

    public static final ResourceLocation ENTITIES_BOKOBLIN = register("enemies/bokoblin");
    public static final ResourceLocation ENTITIES_MOBLIN = register("enemies/moblin");
    // ChuChu
    public static final ResourceLocation ENTITIES_CHUCHU_RED = register("enemies/chuchu_red");
    public static final ResourceLocation ENTITIES_CHUCHU_GREEN = register("enemies/chuchu_green");
    public static final ResourceLocation ENTITIES_CHUCHU_YELLOW = register("enemies/chuchu_yellow");
    public static final ResourceLocation ENTITIES_CHUCHU_BLUE = register("enemies/chuchu_blue");
    public static final ResourceLocation ENTITIES_CHUCHU_BLACK = register("enemies/chuchu_black");

    private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(WindcrafterMod.MODID, id));
    }

}
