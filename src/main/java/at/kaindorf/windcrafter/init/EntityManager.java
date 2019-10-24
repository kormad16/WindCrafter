package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.entities.enemies.EntityBokoblin;
import at.kaindorf.windcrafter.entities.projectiles.EntityFireArrow;
import at.kaindorf.windcrafter.entities.projectiles.EntityIceArrow;
import at.kaindorf.windcrafter.entities.projectiles.EntityLightArrow;
import at.kaindorf.windcrafter.entities.renderers.RendererBokoblin;
import at.kaindorf.windcrafter.entities.renderers.RendererFireArrow;
import at.kaindorf.windcrafter.entities.renderers.RendererIceArrow;
import at.kaindorf.windcrafter.entities.renderers.RendererLightArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityManager {

    private static int nextEntityId = 1;
    private static RegistryEvent.Register<EntityEntry> Event;

    public static void initEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(getEntityWithSpawnEgg(
                EntityBokoblin.class, "bokoblin", 80, 3, true, 0x213C39, 0x526D7B, EnumCreatureType.MONSTER, 100, 2, 4, getBiomesFromStrings(EntityBokoblin.BIOMES)
        ).build());
        event.getRegistry().register(getEntity(EntityFireArrow.class, "firearrow", 64, 5, true).build());
        event.getRegistry().register(getEntity(EntityIceArrow.class, "icearrow", 64, 5, true).build());
        event.getRegistry().register(getEntity(EntityLightArrow.class, "lightarrow", 64, 5, true).build());
    }

    public static EntityEntryBuilder<Entity> getEntity(Class<? extends EntityArrow> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        return EntityEntryBuilder.create()
                .entity(entityClass)
                .id(new ResourceLocation(WindcrafterMod.MODID, entityName), nextEntityId++)
                .name(entityName)
                .tracker(trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    public static EntityEntryBuilder<Entity> getEntity(Class<? extends EntityLiving> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, EnumCreatureType enumCreatureType, int spawnWeight, int spawnMin, int spawnMax, Biome[] biomes) {
        return EntityEntryBuilder.create()
                .entity(entityClass)
                .id(new ResourceLocation(WindcrafterMod.MODID, entityName), nextEntityId++)
                .name(entityName)
                .tracker(trackingRange, updateFrequency, sendsVelocityUpdates)
                .spawn(enumCreatureType, spawnWeight, spawnMin, spawnMax, biomes);
    }

    // register an entity and in addition create a spawn egg for it
    public static EntityEntryBuilder<Entity> getEntityWithSpawnEgg(Class<? extends EntityLiving> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggBackgroundColor, int eggForegroundColor, EnumCreatureType enumCreatureType, int spawnWeight, int spawnMin, int spawnMax, Biome[] biomes) {
        return getEntity(entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates, enumCreatureType, spawnWeight, spawnMin, spawnMax, biomes)
                .egg(eggForegroundColor, eggBackgroundColor);
    }

    public static Biome[] getBiomesFromStrings(String[] biomes) {
        return Arrays.stream(biomes)
                .map(biome -> ForgeRegistries.BIOMES.containsKey(new ResourceLocation(biome)) ? ForgeRegistries.BIOMES.getValue(new ResourceLocation(biome)) : null)
                .filter(biome -> biome != null).collect(Collectors.toSet()).toArray(new Biome[0]);
    }

    @SideOnly(Side.CLIENT)
    public static void initRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBokoblin.class, RendererBokoblin::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, RendererFireArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class, RendererIceArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLightArrow.class, RendererLightArrow::new);
    }

}
