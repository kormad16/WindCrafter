package at.kaindorf.windcrafter.items;

import at.kaindorf.windcrafter.creativetab.ModCreativeTabs;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemEnemyWeapon extends ItemSword {

    public static final String[] ids = {"bokostick", "darknut_sword", "moblin_spear"};
    private static final float[] attackDamage = {2.0f, 6.5f, 3.0f};
    private static final float[] attackSpeed = {-1.5f, -3.5f, -1.75f};
    private static final int[] durability = {50, 200, 100};

    private int index;

    public ItemEnemyWeapon(int num) {
        super(ToolMaterial.WOOD);
        setUnlocalizedName(ids[num]);
        setRegistryName(ids[num]);
        setCreativeTab(ModCreativeTabs.MOD_WEAPONS);
        setMaxDamage(durability[num]);
        this.index = num;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if(slot == EntityEquipmentSlot.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage[index], 0));
            multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed[index], 0));
        }

        return multimap;
    }
}
