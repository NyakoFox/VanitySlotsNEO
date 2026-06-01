package gay.nyako.vanityslots.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gay.nyako.vanityslots.VanitySlots;
import gay.nyako.vanityslots.VanitySlotsItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(
            method = "net/minecraft/world/entity/LivingEntity.lambda$static$0(Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private static ItemStack vanityslots$getVisibleDisguiseItem(Player instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
        if (VanitySlots.hasMobVisibleVanityStack(instance, equipmentSlot))
        {
            return VanitySlots.getVanityStack(instance, equipmentSlot);
        }
        return original.call(instance, equipmentSlot);
    }

    @WrapOperation(
            method = "getVisibilityPercent(Lnet/minecraft/world/entity/Entity;)D",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack vanityslots$getVisibleMobHead(LivingEntity instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
        if (VanitySlots.hasMobVisibleVanityStack(instance, equipmentSlot))
        {
            return VanitySlots.getVanityStack(instance, equipmentSlot);
        }
        return original.call(instance, equipmentSlot);
    }

    @WrapOperation(
            method = "getArmorCoverPercentage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private ItemStack vanityslots$getVisibleStackOrNothing(LivingEntity instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
        ItemStack stack;

        if (VanitySlots.hasMobVisibleVanityStack(instance, equipmentSlot))
        {
            // If we have "mobsReact" on, and we have a vanity stack in this slot, use it
            stack = VanitySlots.getVanityStack(instance, equipmentSlot);
        }
        else
        {
            // Use the original stack
            stack = original.call(instance, equipmentSlot);
        }

        // If it's a familiar stack, it shouldn't provide armor cover
        if (stack.is(VanitySlotsItems.FAMILIAR_PANTS) || stack.is(VanitySlotsItems.FAMILIAR_SHIRT) || stack.is(VanitySlotsItems.FAMILIAR_SNEAKERS) || stack.is(VanitySlotsItems.FAMILIAR_WIG))
        {
            return ItemStack.EMPTY;
        }

        return stack;
    }
}
