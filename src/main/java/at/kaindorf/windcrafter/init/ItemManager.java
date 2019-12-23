package at.kaindorf.windcrafter.init;

import at.kaindorf.windcrafter.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemManager {

    // Hero Weapons
    public static Item HEROS_SWORD = new ItemHerosSword();
    public static Item[] MASTERSWORD = new Item[3];
    static { for(int i = 0; i < MASTERSWORD.length; i++) MASTERSWORD[i] = new ItemMasterSword(i); }

    // Triforce Shards
    public static Item[] TRIFORCESHARD = new Item[8];
    static { for(int i = 0; i < TRIFORCESHARD.length; i++) TRIFORCESHARD[i] = new ItemTriforceShard(i); }

    // Health System
    public static Item HEART = new ItemHeart();
    public static Item HEARTCONTAINER = new ItemHeartContainer();
    public static Item HEARTPIECE = new ItemHeartPiece();

    // Enemy Weapons
    public static Item BOKOSTICK = new ItemEnemyWeapon(0);
    public static Item BURNING_BOKOSTICK = new ItemBurningBokoStick();
    public static Item DARKNUT_SWORD = new ItemEnemyWeapon(1);
    public static Item MOBLIN_SPEAR = new ItemEnemyWeapon(2);
    public static Item MACHETE = new ItemEnemyWeapon(3);
    public static Item STALFOS_CLUB = new ItemEnemyWeapon(4);

    // Enemy Drops
    public static Item JOY_PENDANT = new ItemMobDrop(0);
    public static Item SKULL_NECKLACE = new ItemMobDrop(1);
    public static Item RED_CHUJELLY = new ItemMobDrop(2);
    public static Item GREEN_CHUJELLY = new ItemMobDrop(3);
    public static Item BLUE_CHUJELLY = new ItemMobDrop(4);
    public static Item BOKO_SEED = new ItemMobDrop(5);
    public static Item GOLDEN_FEATHER = new ItemMobDrop(6);
    public static Item KNIGHTS_CREST = new ItemMobDrop(7);

    // Magic System
    public static Item SMALLMAGIC = new ItemMagicJar(false);
    public static Item LARGEMAGIC = new ItemMagicJar(true);

    // Magic Arrows
    public static Item FIREARROW = new ItemFireArrow();
    public static Item ICEARROW = new ItemIceArrow();
    public static Item LIGHTARROW = new ItemLightArrow();

    // Healing Consumables
    public static Item RED_POTION = new ItemHealPotion(0);
    public static Item GREEN_POTION = new ItemHealPotion(1);
    public static Item BLUE_POTION = new ItemHealPotion(2);
    public static Item FAIRY_BOTTLE = new ItemFairyBottle();

    // Armor / Equipment
    public static Item HEROS_CHARM = new ItemHerosCharm();

    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                HEROS_SWORD,
                HEART, HEARTCONTAINER, HEARTPIECE,
                SMALLMAGIC, LARGEMAGIC,
                FIREARROW, ICEARROW, LIGHTARROW,
                BOKOSTICK, BURNING_BOKOSTICK, DARKNUT_SWORD, MOBLIN_SPEAR, MACHETE, STALFOS_CLUB,
                JOY_PENDANT, SKULL_NECKLACE, RED_CHUJELLY, GREEN_CHUJELLY, BLUE_CHUJELLY, BOKO_SEED, GOLDEN_FEATHER, KNIGHTS_CREST,
                RED_POTION, GREEN_POTION, BLUE_POTION, FAIRY_BOTTLE,
                HEROS_CHARM
        );
        e.getRegistry().registerAll(MASTERSWORD);
        e.getRegistry().registerAll(TRIFORCESHARD);
    }

    public static void registerRenders(ModelRegistryEvent event) {
        registerRender(HEROS_SWORD);

        for(Item ms : MASTERSWORD)
            registerRender(ms);

        for(Item ts : TRIFORCESHARD)
            registerRender(ts);

        registerRender(HEART);
        registerRender(HEARTCONTAINER);
        registerRender(HEARTPIECE);

        registerRender(BOKOSTICK);
        registerRender(BURNING_BOKOSTICK);
        registerRender(DARKNUT_SWORD);
        registerRender(MOBLIN_SPEAR);
        registerRender(MACHETE);
        registerRender(STALFOS_CLUB);

        registerRender(SMALLMAGIC);
        registerRender(LARGEMAGIC);

        registerRender(FIREARROW);
        registerRender(ICEARROW);
        registerRender(LIGHTARROW);

        registerRender(JOY_PENDANT);
        registerRender(SKULL_NECKLACE);
        registerRender(RED_CHUJELLY);
        registerRender(GREEN_CHUJELLY);
        registerRender(BLUE_CHUJELLY);
        registerRender(BOKO_SEED);
        registerRender(GOLDEN_FEATHER);
        registerRender(KNIGHTS_CREST);

        registerRender(RED_POTION);
        registerRender(GREEN_POTION);
        registerRender(BLUE_POTION);
        registerRender(FAIRY_BOTTLE);

        registerRender(HEROS_CHARM);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
