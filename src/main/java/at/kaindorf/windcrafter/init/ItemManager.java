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
    public static Item DARKNUT_SWORD = new ItemEnemyWeapon(1);
    public static Item MOBLIN_SPEAR = new ItemEnemyWeapon(2);
    public static Item MACHETE = new ItemEnemyWeapon(3);

    // Enemy Drops
    public static Item JOY_PENDANT = new ItemMobDrop(0);
    public static Item SKULL_NECKLACE = new ItemMobDrop(1);

    // Magic System
    public static Item SMALLMAGIC = new ItemMagicJar(false);
    public static Item LARGEMAGIC = new ItemMagicJar(true);

    // Magic Arrows
    public static Item FIREARROW = new ItemFireArrow();
    public static Item ICEARROW = new ItemIceArrow();
    public static Item LIGHTARROW = new ItemLightArrow();

    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(
                HEROS_SWORD,
                HEART, HEARTCONTAINER, HEARTPIECE,
                SMALLMAGIC, LARGEMAGIC,
                FIREARROW, ICEARROW, LIGHTARROW,
                BOKOSTICK, DARKNUT_SWORD, MOBLIN_SPEAR, MACHETE,
                JOY_PENDANT, SKULL_NECKLACE
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
        registerRender(DARKNUT_SWORD);
        registerRender(MOBLIN_SPEAR);
        registerRender(MACHETE);

        registerRender(SMALLMAGIC);
        registerRender(LARGEMAGIC);

        registerRender(FIREARROW);
        registerRender(ICEARROW);
        registerRender(LIGHTARROW);

        registerRender(JOY_PENDANT);
        registerRender(SKULL_NECKLACE);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
