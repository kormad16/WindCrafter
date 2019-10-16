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
    public static Item[] ENEMYWEAPON = new Item[ItemEnemyWeapon.ids.length];
    static { for(int i = 0; i < ENEMYWEAPON.length; i++) ENEMYWEAPON[i] = new ItemEnemyWeapon(i); }

    // Magic System
    public static Item SMALLMAGIC = new ItemMagicJar(false);
    public static Item LARGEMAGIC = new ItemMagicJar(true);

    // Magic Arrows
    public static Item FIREARROW = new ItemFireArrow();

    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(HEROS_SWORD, HEART, HEARTCONTAINER, HEARTPIECE, SMALLMAGIC, LARGEMAGIC, FIREARROW);
        for(Item ms : MASTERSWORD)
            e.getRegistry().register(ms);
        for(Item ts : TRIFORCESHARD)
            e.getRegistry().register(ts);
        for(Item ew : ENEMYWEAPON)
            e.getRegistry().register(ew);
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
        for(Item ew : ENEMYWEAPON)
            registerRender(ew);
        registerRender(SMALLMAGIC);
        registerRender(LARGEMAGIC);
        registerRender(FIREARROW);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
