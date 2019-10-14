package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.entities.enemies.EntityBokoblin;
import at.kaindorf.windcrafter.entities.renderers.RendererBokoblin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.List;

public class EntityManager {

    private static int nextEntityId = 1;

    public static void initEntities() {
        registerEntityWithSpawnEgg(EntityBokoblin.class, "Bokoblin", 80, 3, true, 0x213C39, 0x526D7B, EnumCreatureType.MONSTER, 5, 1, 5, EntityBokoblin.BIOMES);
    }

    // register an entity
    public static int registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(WindcrafterMod.MODID, entityName), entityClass, entityName, nextEntityId, WindcrafterMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
        //idToBEEntityName.put(nextEntityId, entityName);
        return nextEntityId++;
    }

    // register an entity and in addition create a spawn egg for it
    public static int registerEntityWithSpawnEgg(Class<? extends EntityLiving> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggBackgroundColor, int eggForegroundColor, EnumCreatureType enumCreatureType, int spawnWeight, int spawnMin, int spawnMax, List<String> biomes)
    {
        int ffEntityId = registerEntity(entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates);
        EntityRegistry.registerEgg(new ResourceLocation(WindcrafterMod.MODID, entityName), eggBackgroundColor, eggForegroundColor);
        addSpawn(entityClass, spawnWeight, spawnMin, spawnMax, enumCreatureType, biomes);
        return ffEntityId;
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, List<String> biomes) {
        for (String biomeName : biomes) {
            ResourceLocation loc = new ResourceLocation(biomeName);

            if (ForgeRegistries.BIOMES.containsKey(loc)) {
                Biome biome = ForgeRegistries.BIOMES.getValue(loc);

                List<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature);

                boolean found = false;
                for (SpawnListEntry entry : spawns) {
                    //Adjusting an existing spawn entry
                    if (entry.entityClass == entityClass) {
                        entry.itemWeight = weightedProb;
                        entry.minGroupCount = min;
                        entry.maxGroupCount = max;
                        found = true;
                        break;
                    }
                }

                if (!found) spawns.add(new SpawnListEntry(entityClass, weightedProb, min, max));
            }
        }
    }

    public static void removeSpawn(Class<? extends EntityLiving> entityClass, EnumCreatureType typeOfCreature, List<String> biomes) {
        for (String biomeName : biomes) {
            ResourceLocation loc = new ResourceLocation(biomeName);

            if (ForgeRegistries.BIOMES.containsKey(loc)) {
                Biome biome = ForgeRegistries.BIOMES.getValue(loc);

                Iterator<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature).iterator();

                while (spawns.hasNext()) {
                    SpawnListEntry entry = spawns.next();
                    if (entry.entityClass == entityClass) {
                        spawns.remove();
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBokoblin.class, RendererBokoblin.FACTORY);
    }

}
