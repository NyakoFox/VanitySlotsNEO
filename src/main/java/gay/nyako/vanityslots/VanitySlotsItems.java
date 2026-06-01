package gay.nyako.vanityslots;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.*;

import java.util.function.Function;

public class VanitySlotsItems {
    public static final ResourceKey<EquipmentAsset> VANITY_ARMOR_MATERIAL_KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, VanitySlots.id("vanity"));

    public static final ArmorMaterial VANITY_ARMOR_MATERIAL = new ArmorMaterial(5,
            ArmorMaterials.makeDefense(1, 2, 3, 1, 3),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F, 0.0F, ItemTags.REPAIRS_LEATHER_ARMOR, VANITY_ARMOR_MATERIAL_KEY
    );

    public static final Item FAMILIAR_WIG = register("familiar_wig", Item::new, new Item.Properties().humanoidArmor(VANITY_ARMOR_MATERIAL, ArmorType.HELMET));
    public static final Item FAMILIAR_SHIRT = register("familiar_shirt", Item::new, new Item.Properties().humanoidArmor(VANITY_ARMOR_MATERIAL, ArmorType.CHESTPLATE));
    public static final Item FAMILIAR_PANTS = register("familiar_pants", Item::new, new Item.Properties().humanoidArmor(VANITY_ARMOR_MATERIAL, ArmorType.LEGGINGS));
    public static final Item FAMILIAR_SNEAKERS = register("familiar_sneakers", Item::new, new Item.Properties().humanoidArmor(VANITY_ARMOR_MATERIAL, ArmorType.BOOTS));

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, VanitySlots.id(name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void register() {

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> {
                    itemGroup.accept(FAMILIAR_WIG);
                    itemGroup.accept(FAMILIAR_SHIRT);
                    itemGroup.accept(FAMILIAR_PANTS);
                    itemGroup.accept(FAMILIAR_SNEAKERS);
                });
    }
}
