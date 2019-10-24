package at.kaindorf.windcrafter.util;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeHelper {

    public static Biome[] ALL_LAND;
    static {
        //ALL_LAND = ForgeRegistries.BIOMES.getEntries().stream().filter(b -> b.getKey().toString().contains("ocean"));
    }

}
