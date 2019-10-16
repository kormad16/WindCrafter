package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMasterSword extends ItemSword {

    private static final float[] attackDamage = {7.0f, 8.0f, 10.0f};
    private static final float[] attackSpeed = {-1.2f, -1.2f, -1.2f};
    private static final int[] durability = {10000, 20000, -1};

    public int level;

    public ItemMasterSword(int level) {
        super(Item.ToolMaterial.IRON);
        setUnlocalizedName("mastersword" + (level+1));
        setRegistryName("mastersword" + (level+1));
        setCreativeTab(ModCreativeTabs.MOD_WEAPONS);
        if(durability[level] > -1)
            setMaxDamage(durability[level]);
        this.level = level;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if(slot == EntityEquipmentSlot.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage[level], 0));
            multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed[level], 0));
        }

        return multimap;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if(durability[level] > -1)
            super.setDamage(stack, damage);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return ((ItemMasterSword)(stack.getItem())).level == 2;
    }
}
