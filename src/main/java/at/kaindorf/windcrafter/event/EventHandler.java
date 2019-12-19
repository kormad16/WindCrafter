package at.kaindorf.windcrafter.event;

import at.kaindorf.windcrafter.WindcrafterMod;
import at.kaindorf.windcrafter.blocks.BlockBrokenBarricade;
import at.kaindorf.windcrafter.gui.GuiZeldaHealth;
import at.kaindorf.windcrafter.gui.GuiZeldaMagic;
import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.init.SoundManager;
import at.kaindorf.windcrafter.items.ItemHerosCharm;
import at.kaindorf.windcrafter.items.ItemHerosSword;
import at.kaindorf.windcrafter.items.ItemMagicJar;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

@Mod.EventBusSubscriber(modid = WindcrafterMod.MODID)
public class EventHandler {

    // Zelda Health System: Keep Hearts on Respawn
    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone e) {
        double health = e.getOriginal().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
        e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
        e.getEntityPlayer().setHealth((float)health);
        e.getEntityPlayer().getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC, e.getOriginal().getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX));
        e.getEntityPlayer().getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC_MAX, e.getOriginal().getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX));
    }

    public static final GuiZeldaHealth HEALTH_GUI = new GuiZeldaHealth(Minecraft.getMinecraft());
    public static final GuiZeldaMagic MAGIC_GUI = new GuiZeldaMagic(Minecraft.getMinecraft());

    public static final float FAIRY_PARTICLE_MAX = 80;
    public static final int HEROS_CHARM_BAR_LENGTH = 20;

    private static Map<UUID, String> tagNameMap = new HashMap<>();

    // Zelda Health System: Modified Health Display
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre e) {
        if(e.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            e.setCanceled(true);
            HEALTH_GUI.drawHealth(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        } else if(e.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            e.setCanceled(true);
            MAGIC_GUI.drawMagic(e.getResolution().getScaledWidth(), e.getResolution().getScaledHeight());
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingAttackEvent e) {
        if(e.getEntity() instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)e.getEntity();
            if(p.getHealth() - e.getAmount() <= 0 && p.inventory.hasItemStack(new ItemStack(ItemManager.FAIRY_BOTTLE))) {
                p.inventory.clearMatchingItems(ItemManager.FAIRY_BOTTLE, 0, 1, null);
                p.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
                p.playSound(SoundManager.fairyHealSoundEvent, 1.0f, 1.0f);
                p.setHealth((float)(p.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue()/2));
                p.performHurtAnimation();
                p.getEntityData().setInteger("FairyParticleTimer",  (int)FAIRY_PARTICLE_MAX);
                e.setCanceled(true);
            }
        }
    }

    private static void spawnFairyParticles(int particleCount, EntityPlayer p, int timer)
    {
        if (particleCount > 0)
        {
            World world = Minecraft.getMinecraft().world;
            Random rand = new Random();
            for (int j = 0; j < particleCount; ++j)
            {
                world.spawnParticle(EnumParticleTypes.CRIT, p.posX + Math.sin(0.25*(timer+(1.0/particleCount)*j)) * p.width, p.posY + p.height - p.height*(timer/FAIRY_PARTICLE_MAX), p.posZ + Math.cos(0.25*(timer+(1.0/particleCount)*j)) * (double)p.width, 0,-0.0125,0);
            }
        }
    }

    private static String getHealthString(float health, float maxHealth) {
        int greenCount = (int)Math.ceil((health/maxHealth) * HEROS_CHARM_BAR_LENGTH);
        int redCount = HEROS_CHARM_BAR_LENGTH - greenCount;

        String healthStr = "\u00A72";
        for(int i = 0; i < greenCount; i++)
            healthStr += "|";
        healthStr += "\u00A7r\u00A74";
        for(int i = 0; i < redCount; i++)
            healthStr += "|";
        return healthStr + "\u00A7r";
    }

    // Player Tick Event
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        // Init Magic
        if(!e.player.getDataManager().getAll().contains(GuiZeldaMagic.ZELDA_MAGIC_INIT) && (!e.player.getDataManager().getAll().contains(GuiZeldaMagic.ZELDA_MAGIC) || !e.player.getDataManager().getAll().contains(GuiZeldaMagic.ZELDA_MAGIC_MAX))) {
            try {
                e.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC, 100);
                e.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC_MAX, 100);
                e.player.getDataManager().register(GuiZeldaMagic.ZELDA_MAGIC_INIT, Boolean.TRUE);
            } catch(Exception ex) {}
        }

        // Hero's Charm
        try {
            if (!e.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && e.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemHerosCharm) {
                List<Entity> entities = Minecraft.getMinecraft().world.loadedEntityList;
                for (Entity entity : entities) {
                    if (!(entity instanceof EntityPlayer) && entity instanceof EntityLiving) {
                        if (!tagNameMap.containsKey(entity.getUniqueID()))
                            tagNameMap.put(entity.getUniqueID(), entity.getCustomNameTag());
                        entity.setCustomNameTag((tagNameMap.get(entity.getCustomNameTag()) != null ? " " + tagNameMap.get(entity.getCustomNameTag()) : "") + getHealthString(((EntityLiving) entity).getHealth(), (float) ((EntityLiving) entity).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue()));
                    }
                }
            } else {
                List<Entity> entities = Minecraft.getMinecraft().world.loadedEntityList;
                for (Entity entity : entities) {
                    if (tagNameMap.containsKey(entity.getUniqueID())) {
                            entity.setCustomNameTag(tagNameMap.get(entity.getCustomNameTag()) != null ? tagNameMap.get(entity.getCustomNameTag()) : "");
                        tagNameMap.remove(entity.getUniqueID());
                    }
                }
            }
        } catch(Exception ex) {}
        // Fairy Particles
        if(e.player.getEntityData().hasKey("FairyParticleTimer") && e.player.getEntityData().getInteger("FairyParticleTimer") > 0) {
            int fairytimer = e.player.getEntityData().getInteger("FairyParticleTimer");
            spawnFairyParticles(5, e.player, fairytimer);
            e.player.getEntityData().setInteger("FairyParticleTimer", fairytimer-1);
        }
        // Heart
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEART))) {
            e.player.playSound(SoundManager.heartSoundEvent, 1.0f, 1.0f);
            e.player.heal(2.0f);
            e.player.inventory.clearMatchingItems(ItemManager.HEART, 0, 1, null);
        }
        // Heart Piece
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEARTPIECE))) {
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40) {
                health.setBaseValue(health.getBaseValue() + 0.5f);
                if(health.getBaseValue() % 2 == 0)
                    e.player.playSound(SoundManager.heartContainerSoundEvent, 1.0f, 1.0f);
                else
                    e.player.playSound(SoundManager.itemFanfareSoundEvent, 1.0f, 1.0f);
            }
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(ItemManager.HEARTPIECE, 0, 1, null);
        }
        // Heart Container
        while (e.player.inventory.hasItemStack(new ItemStack(ItemManager.HEARTCONTAINER))) {
            e.player.playSound(SoundManager.heartContainerSoundEvent, 1.0f, 1.0f);
            IAttributeInstance health = e.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            if(health.getBaseValue() < 40)
                health.setBaseValue(health.getBaseValue() + 2);
            e.player.heal((float)health.getBaseValue());
            e.player.inventory.clearMatchingItems(ItemManager.HEARTCONTAINER, 0, 1, null);
        }
        // Small Magic Jar
        while(e.player.inventory.hasItemStack(new ItemStack(ItemManager.SMALLMAGIC))) {
            e.player.playSound(SoundManager.smallPickupSoundEvent, 1.0f, 1.0f);
            if(e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) < e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX)) {
                e.player.getDataManager().set(GuiZeldaMagic.ZELDA_MAGIC, e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) + 20);
                if (e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) > e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX))
                    e.player.getDataManager().set(GuiZeldaMagic.ZELDA_MAGIC, e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX));
            }
            e.player.inventory.clearMatchingItems(ItemManager.SMALLMAGIC, 0, 1, null);
        }
        // Large Magic Jar
        while(e.player.inventory.hasItemStack(new ItemStack(ItemManager.LARGEMAGIC))) {
            e.player.playSound(SoundManager.smallPickupSoundEvent, 1.0f, 1.0f);
            if(e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) < e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX)) {
                e.player.getDataManager().set(GuiZeldaMagic.ZELDA_MAGIC, e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) + 50);
                if (e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC) > e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX))
                    e.player.getDataManager().set(GuiZeldaMagic.ZELDA_MAGIC, e.player.getDataManager().get(GuiZeldaMagic.ZELDA_MAGIC_MAX));
            }
            e.player.inventory.clearMatchingItems(ItemManager.LARGEMAGIC, 0, 1, null);
        }

        // Low Health Sound
        if(!e.player.getEntityData().hasKey("LowHealthTimer"))
            e.player.getEntityData().setByte("LowHealthTimer", (byte)0);
        if(e.player.getHealth() < 4.0f && e.player.getHealth() > 0.0f) {
            if(e.player.getEntityData().getByte("LowHealthTimer") == 0) {
                e.player.playSound(SoundManager.lowHealthSoundEvent, 1.0f, 1.0f);
                e.player.getEntityData().setByte("LowHealthTimer", (byte)100);
            } else
                e.player.getEntityData().setByte("LowHealthTimer", (byte)(e.player.getEntityData().getByte("LowHealthTimer")-1));
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        if(e.getState().getBlock() instanceof BlockBrokenBarricade) {
            BlockBrokenBarricade block = (BlockBrokenBarricade) e.getState().getBlock();
            BlockPos pos = e.getPos();
            World worldIn = e.getWorld();
            EntityPlayer p = e.getPlayer();

            if(p.getHeldItem(EnumHand.MAIN_HAND).isItemEnchantable() && p.getHeldItem(EnumHand.MAIN_HAND).getMaxDamage() > 0) {
                breakBrokenBarricadeBlock(block, pos, worldIn);
                worldIn.playSound(p, pos, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
            else if(!p.isCreative())
                e.setCanceled(true);
        }
    }

    public static void breakBrokenBarricadeBlock(BlockBrokenBarricade block, BlockPos pos, World worldIn) {
        worldIn.destroyBlock(pos, false);
        worldIn.tick();
        for(int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
            for(int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                for(int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
                    BlockPos position = new BlockPos(x,y,z);
                    if(worldIn.getBlockState(position).getBlock() instanceof BlockBrokenBarricade) {
                        BlockBrokenBarricade barricadeBlock = (BlockBrokenBarricade)worldIn.getBlockState(position).getBlock();
                        breakBrokenBarricadeBlock(barricadeBlock, position, worldIn);
                    }
                }
            }
        }
    }

}
