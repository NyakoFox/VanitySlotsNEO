package gay.nyako.vanityslots.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gay.nyako.vanityslots.VanitySlots;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @WrapOperation(
            method = "isWearingSafeArmor(Lnet/minecraft/world/entity/LivingEntity;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
    )
    private static ItemStack vanityslots$getVisibleArmor(LivingEntity instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
        if (VanitySlots.hasMobVisibleVanityStack(instance, equipmentSlot))
        {
            return VanitySlots.getVanityStack(instance, equipmentSlot);
        }
        return original.call(instance, equipmentSlot);
    }
}
