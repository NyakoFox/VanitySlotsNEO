package gay.nyako.vanityslots;

import dev.emi.trinkets.api.SlotType;
import dev.emi.trinkets.api.TrinketsApi;
import dev.toma.configuration.Configuration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class VanitySlots implements ModInitializer {
	public static final String MOD_ID = "vanityslots";
	public static VanitySlotsConfig config;

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static final TagKey<Item> VANITY_BLACKLIST = TagKey.create(BuiltInRegistries.ITEM.key(), id("vanity_blacklist"));

	@Override
	public void onInitialize() {
		registerPredicate("head", EquipmentSlot.HEAD);
		registerPredicate("chest", EquipmentSlot.CHEST);
		registerPredicate("legs", EquipmentSlot.LEGS);
		registerPredicate("feet", EquipmentSlot.FEET);

		config = Configuration.registerSimpleJsonConfig(VanitySlotsConfig.class);

		VanitySlotsItems.register();
	}

	private static boolean matches(EquipmentSlot slot, String group)
	{
		return switch (slot)
		{
			case FEET  -> group.equals("feet");
			case LEGS  -> group.equals("legs");
			case CHEST -> group.equals("chest");
			case HEAD  -> group.equals("head");
			default    -> false;
		};
	}

	/**
	 * Checks if the given entity has a vanity stack in the given slot, intended for mobs AI to react to.
	 * If "mobsReact" is false, this will return false, as mobs shouldn't react to vanity stacks.
	 * Additionally, this returns false for any non-player entity.
	 */
	public static boolean hasMobVisibleVanityStack(LivingEntity livingEntity, EquipmentSlot equipmentSlot) {
		if (VanitySlots.config.mobsReact && livingEntity instanceof Player)
		{
			ItemStack vanityStack = VanitySlots.getVanityStack(livingEntity, equipmentSlot);
            return !vanityStack.isEmpty();
		}

		return false;
	}

	/**
	 * Gets the vanity stack for the given entity and slot, intended for rendering and AI purposes.
	 * This will return an empty stack if there is no vanity stack, or if the entity isn't a player.
	 */
	public static ItemStack getVanityStack(LivingEntity entity, EquipmentSlot slot)
	{
		if (!(entity instanceof Player))
			return ItemStack.EMPTY;

		return TrinketsApi.getTrinketComponent(entity)
				.map(component -> {
					for (var equipped : component.getAllEquipped())
					{
						SlotType slotType = equipped.getA().inventory().getSlotType();
						ItemStack stack = equipped.getB();

						if (stack.isEmpty())
							continue;

						if (!slotType.getName().equals("vanity"))
							continue;

						if (matches(slot, slotType.getGroup()))
							return stack;
					}
					return ItemStack.EMPTY;
				})
				.orElse(ItemStack.EMPTY);
	}

	public void registerPredicate(String identifier, EquipmentSlot slot) {
		TrinketsApi.registerTrinketPredicate(Identifier.fromNamespaceAndPath(MOD_ID, identifier), (stack, ref, entity) -> {
			if (entity.isEquippableInSlot(stack, slot)) {
				if (EnchantmentHelper.has(stack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE))
				{
					// Don't allow curse of binding in our vanity slots...
					return TriState.FALSE;
				}

				if (stack.is(VANITY_BLACKLIST)) {
					// If the item is in the blacklist, don't allow it in our vanity slots.
					return TriState.FALSE;
				}

				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		});

		TrinketsApi.registerTrinketPredicate(Identifier.fromNamespaceAndPath(MOD_ID, "quick_" + identifier), (stack, ref, entity) -> {
			// If the vanilla slot is empty...
			if (entity.getItemBySlot(slot).isEmpty()) {
				// We don't want to shift click into our custom one.
				return TriState.FALSE;
			}
			// There's something in the vanilla slot, so shift
			// clicking into the vanity slot should be allowed.
			return TriState.TRUE;
		});
	}
}