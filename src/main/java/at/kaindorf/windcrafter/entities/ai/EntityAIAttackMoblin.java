package at.kaindorf.windcrafter.entities.ai;

import at.kaindorf.windcrafter.entities.enemies.EntityBokoblin;
import at.kaindorf.windcrafter.entities.enemies.EntityMoblin;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityAIAttackMoblin extends EntityAIAttackMelee {

    private EntityMoblin moblin;

    public EntityAIAttackMoblin(EntityMoblin moblinIn, double speedIn, boolean useLongMemory) {
        super(moblinIn, speedIn, useLongMemory);
        this.moblin = moblinIn;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        super.resetTask();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
    }

}
