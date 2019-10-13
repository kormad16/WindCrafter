package at.kaindorf.windcrafter.entities.enemies;

import at.kaindorf.windcrafter.entities.ai.EntityAIAttackBokoblin;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import java.util.List;

public class EntityBokoblin extends EntityMob {
    /** The attribute which determines the chance that this mob will spawn reinforcements */
    protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute((IAttribute)null, "bokoblin.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");

    private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean>createKey(EntityZombie.class, DataSerializers.BOOLEAN);
    private float bokoblinWidth = -1.0F;
    private float bokoblinHeight;

    public static final List<String> BIOMES = Lists.newArrayList("minecraft:birch_forest", "minecraft:birch_forest_hills", "minecraft:mutated_birch_forest",
            "minecraft:mutated_birch_forest_hills", "minecraft:forest", "minecraft:forest_hills", "minecraft:mutated_forest",
            "minecraft:taiga", "minecraft:taiga_hills", "minecraft:mutated_taiga", "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills",
            "minecraft:mutated_redwood_taiga", "minecraft:mutated_redwood_taiga_hills", "minecraft:taiga_cold", "minecraft:taiga_cold_hills",
            "minecraft:mutated_taiga_cold", "minecraft:extreme_hills", "minecraft:smaller_extreme_hills", "minecraft:extreme_hills_with_trees",
            "minecraft:mutated_extreme_hills", "minecraft:mutated_extreme_hills_with_trees", "minecraft:roofed_forest", "minecraft:mutated_roofed_forest",

            "biomesoplenty:boreal_forest", "biomesoplenty:coniferous_forest", "biomesoplenty:dead_forest", "biomesoplenty:grove", "biomesoplenty:maple_woods",
            "biomesoplenty:mountain_foothills", "biomesoplenty:redwood_forest", "biomesoplenty:redwood_forest", "biomesoplenty:seasonal_forest",
            "biomesoplenty:shield", "biomesoplenty:snowy_coniferous_forest", "biomesoplenty:snowy_forest", "biomesoplenty:temperate_rainforest",
            "biomesoplenty:woodland",

            "traverse:autumnal_woods", "traverse:autumnal_wooded_hills", "traverse:birch_forested_hills", "traverse:foreseted_hills",
            "traverse:snowy_coniferous_forest", "traverse:temperate_rainforest", "traverse:woodlands",
            "conquest:boreal_forest", "conquest:birch_forest", "conquest:deciduous_forest", "conquest:oldgrowth_forest", "conquest:lorein_forest",
            "conquest:mountains", "conquest:mega_boreal_forest",
            "twilightforest:dense_twilight_forest", "twilightforest:snowy_forest", "twilightforest:twilight_highlands");

    public EntityBokoblin (World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.7F);
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackBokoblin(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
        this.getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.rand.nextDouble() * net.minecraftforge.common.ForgeModContainer.zombieSummonBaseChance);
    }

    protected void entityInit()
    {
        super.entityInit();
        //this.getDataManager().register(VILLAGER_TYPE, Integer.valueOf(0)); TODO: add profession (blue, green, pink)
        this.getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
    }

    public void setArmsRaised(boolean armsRaised)
    {
        this.getDataManager().set(ARMS_RAISED, Boolean.valueOf(armsRaised));
    }

    @SideOnly(Side.CLIENT)
    public boolean isArmsRaised()
    {
        return ((Boolean)this.getDataManager().get(ARMS_RAISED)).booleanValue();
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(EntityPlayer player)
    {
        if (this.isChild())
        {
            this.experienceValue = (int)((float)this.experienceValue * 2.5F);
        }
        return super.getExperiencePoints(player);
    }

    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
//        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild() && this.shouldBurnInDay())
//        {
//            float f = this.getBrightness();
//
//            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
//            {
//                boolean flag = true;
//                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
//
//                if (!itemstack.isEmpty())
//                {
//                    if (itemstack.isItemStackDamageable())
//                    {
//                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
//
//                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
//                        {
//                            this.renderBrokenItemStack(itemstack);
//                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
//                        }
//                    }
//
//                    flag = false;
//                }
//
//                if (flag)
//                {
//                    this.setFire(8);
//                }
//            }
//        }

        super.onLivingUpdate();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        System.out.println(amount+" - "+getHealth());
        return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F)
            {
                entityIn.setFire(2 * (int)f);
            }
        }

        return flag;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_ZOMBIE;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);

        if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
        {
            int i = this.rand.nextInt(3);

            if (i == 0)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            }
            else
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }

    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLivingBase entityLivingIn)
    {
        super.onKillEntity(entityLivingIn);
    }

    public float getEyeHeight()
    {
        return 1.65F;
    }

    protected boolean canEquipItem(ItemStack stack)
    {
        return stack.getItem() == Items.EGG && this.isChild() && this.isRiding() ? false : super.canEquipItem(stack);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        float f = difficulty.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(true);

        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);

        if (this.rand.nextFloat() < f * 0.05F)
        {
            this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader bokoblin bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader bokoblin bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
        }

        return livingdata;
    }

    /**
     * Sets the width and height of the entity.
     */
    protected final void setSize(float width, float height)
    {
        boolean flag = this.bokoblinWidth > 0.0F && this.bokoblinHeight > 0.0F;
        this.bokoblinWidth = width;
        this.bokoblinHeight = height;

        if (!flag)
        {
            this.multiplySize(1.0F);
        }
    }

    /**
     * Multiplies the height and width by the provided float.
     */
    protected final void multiplySize(float size)
    {
        super.setSize(this.bokoblinWidth * size, this.bokoblinHeight * size);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return -0.45D;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) // TODO: add drop
    {
        super.onDeath(cause);

//        if (cause.getTrueSource() instanceof EntityCreeper)
//        {
//            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();
//
//            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
//            {
//                entitycreeper.incrementDroppedSkulls();
//                ItemStack itemstack = this.getSkullDrop();
//
//                if (!itemstack.isEmpty())
//                {
//                    this.entityDropItem(itemstack, 0.0F);
//                }
//            }
//        }
    }

}
