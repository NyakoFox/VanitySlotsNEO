package gay.nyako.vanityslots.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gay.nyako.vanityslots.VanitySlots;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {
	@WrapMethod(method = "getEquipmentIfRenderable")
	private static ItemStack vanityslots$getRenderEquipment(LivingEntity livingEntity, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
		ItemStack vanityStack = VanitySlots.getVanityStack(livingEntity, equipmentSlot);
		if (!vanityStack.isEmpty())
		{
			return vanityStack;
		}

		return original.call(livingEntity, equipmentSlot);
	}
}
