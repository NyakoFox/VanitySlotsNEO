package gay.nyako.vanityslots.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import gay.nyako.vanityslots.VanitySlots;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
		extends EntityRenderer<T, S>
		implements RenderLayerParent<S, M> {
	protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
		super(context);
	}

	@WrapOperation(
			method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
			at = @At(value = "INVOKE", target = "getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;")
	)
	private ItemStack vanityslots$replaceHeadItem(LivingEntity instance, EquipmentSlot equipmentSlot, Operation<ItemStack> original) {
		ItemStack stack = VanitySlots.getVanityStack(instance, equipmentSlot);
		if (!stack.isEmpty())
		{
			return stack;
		}

		return original.call(instance, equipmentSlot);
	}
}
