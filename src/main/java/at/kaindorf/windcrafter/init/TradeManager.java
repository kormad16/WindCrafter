package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.entities.villager.ClericMobDropTrades;
import at.kaindorf.windcrafter.entities.villager.NitwitMobDropTrades;
import at.kaindorf.windcrafter.entities.villager.WeaponsmithMobDropTrades;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class TradeManager {

    private static final VillagerRegistry.VillagerCareer FARMER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(1);
    private static final VillagerRegistry.VillagerCareer FISHERMAN = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(2);
    private static final VillagerRegistry.VillagerCareer SHEPHERD = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(3);
    private static final VillagerRegistry.VillagerCareer FLETCHER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:farmer")).getCareer(4);

    private static final VillagerRegistry.VillagerCareer LIBRARIAN = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:librarian")).getCareer(1);
    private static final VillagerRegistry.VillagerCareer CARTOGRAPHER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:librarian")).getCareer(2);

    private static final VillagerRegistry.VillagerCareer CLERIC = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:priest")).getCareer(1);

    private static final VillagerRegistry.VillagerCareer WEAPON_SMITH = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith")).getCareer(1);
    private static final VillagerRegistry.VillagerCareer TOOL_SMITH = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith")).getCareer(2);
    private static final VillagerRegistry.VillagerCareer ARMORER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith")).getCareer(3);

    private static final VillagerRegistry.VillagerCareer BUTCHER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:butcher")).getCareer(1);
    private static final VillagerRegistry.VillagerCareer LEATHERWORKER = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:butcher")).getCareer(2);

    private static final VillagerRegistry.VillagerCareer NITWIT = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:nitwit")).getCareer(1);

    public static void initVillagerTrades() {
        NITWIT.addTrade(1, new NitwitMobDropTrades());
        CLERIC.addTrade(1, new ClericMobDropTrades());
        WEAPON_SMITH.addTrade(1, new WeaponsmithMobDropTrades());
    }

}
