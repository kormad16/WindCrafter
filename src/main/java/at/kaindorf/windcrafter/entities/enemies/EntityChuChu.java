package at.kaindorf.windcrafter.entities.enemies;

import javax.annotation.Nullable;

import at.kaindorf.windcrafter.init.ItemManager;
import at.kaindorf.windcrafter.init.SoundManager;
import at.kaindorf.windcrafter.entities.WindcrafterLootTable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityChuChu extends EntityLiving implements IMob {

    public static final String[] BIOMES = {
            "minecraft:plains", "minecraft:desert", "minecraft:extreme_hills", "minecraft:forest", "minecraft:taiga", "minecraft:swampland",
            "minecraft:mutated_forest", "minecraft:taiga", "minecraft:taiga_hills", "minecraft:mutated_taiga", "minecraft:redwood_taiga", "minecraft:redwood_taiga_hills",
            "minecraft:mutated_redwood_taiga", "minecraft:mutated_redwood_taiga_hills", "minecraft:taiga_cold", "minecraft:taiga_cold_hills",
            "minecraft:mutated_taiga_cold", "minecraft:extreme_hills", "minecraft:smaller_extreme_hills", "minecraft:extreme_hills_with_trees",
            "minecraft:mutated_extreme_hills", "minecraft:mutated_extreme_hills_with_trees", "minecraft:roofed_forest", "minecraft:mutated_roofed_forest"
    };

    private static final DataParameter<Byte> PROFESSION = EntityDataManager.<Byte>createKey(EntityChuChu.class, DataSerializers.BYTE);
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;
    private boolean isCharged = true;

    public EntityChuChu(World worldIn) {
        super(worldIn);
        this.height = 1F;
        this.width = 0.65F;
        this.moveHelper = new EntityChuChu.ChuChuMoveHelper(this);
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityChuChu.AIChuChuFloat(this));
        this.tasks.addTask(2, new EntityChuChu.AIChuChuAttack(this));
        this.tasks.addTask(3, new EntityChuChu.AIChuChuFaceRandom(this));
        //this.tasks.addTask(5, new EntityChuChu.AIChuChuHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(PROFESSION, Byte.valueOf((byte) 0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }


    /**
     * Returns the profession of the chuChu.
     */
    public int getProfession() {
        return (this.dataManager.get(PROFESSION)).intValue();
    }

    public void setProfession(byte profession) {
        this.dataManager.set(PROFESSION, Byte.valueOf(profession));
    }

    public boolean isCharged() {
        return isCharged;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("profession", this.getProfession());
        compound.setBoolean("wasOnGround", this.wasOnGround);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.wasOnGround = compound.getBoolean("wasOnGround");
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.isDead = true;
        }

        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.onUpdate();

        if (this.onGround && !this.wasOnGround) {
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.squishAmount = -0.5F;
        } else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1.0F;
        }
//        else if (((ChuChuMoveHelper) this.getMoveHelper()).isCharging) {
//            this.squishAmount = -0.5f;
//        }

        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    /**
     * Gets the amount of time the chuChu needs to wait between jumps.
     */
//    protected int getJumpDelay() {
//        return this.rand.nextInt(200) + 100;
//    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (entityIn.getEntityData().getInteger("DamageCoolDown") > 0) return;
        this.dealDamage(entityIn);
        if (this.isCharged()) {
            entityIn.getEntityData().setInteger("DamageCoolDown", 100);
            for(PotionEffect p : PotionTypes.SLOWNESS.getEffects())
                entityIn.addPotionEffect(new PotionEffect(p.getPotion(), 85, 50));
        } else {
            entityIn.getEntityData().setInteger("DamageCoolDown", 5);
        }
    }

    protected void dealDamage(EntityLivingBase entityIn) {
        if (this.canEntityBeSeen(entityIn) && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttackStrength())) {
            this.applyEnchantments(this, entityIn);
        }
    }

    public float getEyeHeight() {
        return 0.8F;
    }

    /**
     * Gets the amount of damage dealt to the player when "attacked" by the ChuChu.
     */
    protected int getAttackStrength() {
        return 1;
    }

    protected SoundEvent getAmbientSound() {
        return SoundManager.chuChuAmbient;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundManager.chuChuHurt;
    }

    protected SoundEvent getDeathSound() {
        return SoundManager.chuChuDeath;
    }

    protected SoundEvent getSquishSound() {
        return SoundManager.chuChuSquish;
    }

    @Override
    protected SoundEvent getFallSound(int heightIn) {
        return SoundManager.chuChuFall;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        switch (getProfession()) {
            case 1: return WindcrafterLootTable.ENTITIES_CHUCHU_GREEN;
            case 2: return WindcrafterLootTable.ENTITIES_CHUCHU_YELLOW;
            case 3: return WindcrafterLootTable.ENTITIES_CHUCHU_BLUE;
            case 4: return WindcrafterLootTable.ENTITIES_CHUCHU_BLACK;
            default: return WindcrafterLootTable.ENTITIES_CHUCHU_RED;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {
        this.motionY = 0.5;
        this.isAirBorne = true;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        IEntityLivingData livingData = super.onInitialSpawn(difficulty, livingdata);

        this.setProfession((byte) this.rand.nextInt(5));

        return livingData;
    }

    protected SoundEvent getJumpSound() {
        return SoundManager.chuChuJump;
    }

    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        if (cause.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)cause.getTrueSource();
            if (p.getHealth() != p.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue()) {
                if (this.world.rand.nextDouble() < 0.75) this.entityDropItem(new ItemStack(ItemManager.HEART), 0.0f);
            }
        }
    }

    static class AIChuChuAttack extends EntityAIBase {
        private final EntityChuChu chuChu;
        private int growTieredTimer;

        public AIChuChuAttack(EntityChuChu chuChuIn) {
            this.chuChu = chuChuIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.chuChu.getAttackTarget();

            if (entitylivingbase == null) {
                return false;
            } else if (!entitylivingbase.isEntityAlive()) {
                return false;
            } else {
                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).capabilities.disableDamage;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.growTieredTimer = 300;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            EntityLivingBase entitylivingbase = this.chuChu.getAttackTarget();
            boolean ret;
            if (entitylivingbase == null) {
                ret = false;
            } else if (!entitylivingbase.isEntityAlive()) {
                ret = false;
            } else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.disableDamage) {
                ret = false;
            } else {
                ret = --this.growTieredTimer > 0;
            }
            ((EntityChuChu.ChuChuMoveHelper) this.chuChu.getMoveHelper()).isAttacking = ret;
            return ret;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            this.chuChu.faceEntity(this.chuChu.getAttackTarget(), 10.0F, 10.0F);
            ((EntityChuChu.ChuChuMoveHelper) this.chuChu.getMoveHelper()).setDirection(this.chuChu.rotationYaw);
        }
    }

    static class AIChuChuFaceRandom extends EntityAIBase {
        private final EntityChuChu chuChu;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public AIChuChuFaceRandom(EntityChuChu chuChuIn) {
            this.chuChu = chuChuIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return this.chuChu.getAttackTarget() == null && (this.chuChu.onGround || this.chuChu.isInWater() || this.chuChu.isInLava() || this.chuChu.isPotionActive(MobEffects.LEVITATION));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = 80 + this.chuChu.getRNG().nextInt(120);
                this.chosenDegrees = (float) this.chuChu.getRNG().nextInt(45);
            }

            ((EntityChuChu.ChuChuMoveHelper) this.chuChu.getMoveHelper()).setDirection(this.chosenDegrees);
        }
    }

    static class AIChuChuFloat extends EntityAIBase {
        private final EntityChuChu chuChu;

        public AIChuChuFloat(EntityChuChu chuChuIn) {
            this.chuChu = chuChuIn;
            this.setMutexBits(5);
            ((PathNavigateGround) chuChuIn.getNavigator()).setCanSwim(true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return this.chuChu.isInWater() || this.chuChu.isInLava();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (this.chuChu.getRNG().nextFloat() < 0.8F) {
                this.chuChu.getJumpHelper().setJumping();
            }

//            ((EntityChuChu.ChuChuMoveHelper) this.chuChu.getMoveHelper()).setSpeed(0.5D);
        }
    }

    static class AIChuChuHop extends EntityAIBase {
        private final EntityChuChu chuChu;

        public AIChuChuHop(EntityChuChu chuChuIn) {
            this.chuChu = chuChuIn;
            this.setMutexBits(5);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
//        public void updateTask() {
//            ((EntityChuChu.ChuChuMoveHelper) this.chuChu.getMoveHelper()).setSpeed(2D);
//        }
    }

    static class ChuChuMoveHelper extends EntityMoveHelper {
        private float yRot;
//        public int jumpDelay;
        private final EntityChuChu chuChu;
        private float mvmProg;
        private int wait;
        private int jumpCharge;
        public boolean isAttacking;
        public boolean isCharging;

        public ChuChuMoveHelper(EntityChuChu chuChuIn) {
            super(chuChuIn);
            this.chuChu = chuChuIn;
            this.yRot = 180.0F * chuChu.rotationYaw / (float) Math.PI;
            this.action = EntityMoveHelper.Action.MOVE_TO;
            this.mvmProg = 0;
            this.wait = 0;
            this.jumpCharge = chuChu.getRNG().nextInt(25) + 25;
            this.isAttacking = false;
//            this.jumpDelay = this.chuChu.getJumpDelay();
        }

        public void setDirection(float rotation) {
            if (!this.chuChu.isAirBorne && !this.isCharging) this.yRot = rotation;
        }

//        public void setSpeed(double speedIn) {
//            this.speed = speedIn;
//        }

        public void onUpdateMoveHelper() {
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;

            if (this.action != EntityMoveHelper.Action.MOVE_TO) {
                this.entity.setMoveForward(0.0F);
            } else {
                if (this.entity.onGround) {
                    if (this.isAttacking && chuChu.getDistance(chuChu.getAttackTarget()) < 5/*this.jumpDelay-- <= 0*/) {
                        if (!isCharging)
                            this.chuChu.faceEntity(this.chuChu.getAttackTarget(), 10.0F, 10.0F);
                        this.entity.setAIMoveSpeed(0);
                        if (jumpCharge-- <= 0) {
                            this.isCharging = false;
                            jumpCharge = chuChu.getRNG().nextInt(25) + 25;
//                            this.jumpDelay = this.chuChu.getJumpDelay();
                            this.entity.setAIMoveSpeed(0.25F + chuChu.getRNG().nextFloat());
                            this.chuChu.getJumpHelper().setJumping();
                            this.chuChu.playSound(this.chuChu.getJumpSound(), this.chuChu.getSoundVolume(), ((this.chuChu.getRNG().nextFloat() - this.chuChu.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                            mvmProg = 0;
                        } else {
                            this.isCharging = true;
                        }
                    } else {
                        jumpCharge = chuChu.getRNG().nextInt(25) + 25;
                        isCharging = false;
                        if (wait <= 0) {
                            this.chuChu.moveStrafing = 0.0F;
                            this.chuChu.moveForward = 0.0F;
                            this.entity.setAIMoveSpeed((float) this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * MathHelper.sin(mvmProg) * (this.isAttacking ? 1f : 0.6f));
                            mvmProg += this.isAttacking ? 0.4f : 0.2f;
                            if (mvmProg >= Math.PI) {
                                mvmProg = 0;
                                wait = this.isAttacking ? 10 : 25;
                                this.entity.setAIMoveSpeed(0);
                            }
                        } else {
                            wait--;
                        }
                    }
                } else {
                    this.entity.setAIMoveSpeed((float)this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                }
            }
        }
    }
}