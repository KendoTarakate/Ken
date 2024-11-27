package net.soracraft.soracraftskin.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import net.soracraft.soracraftskin.SkinClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerSkinMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void overrideSkinTexture(AbstractClientPlayerEntity player, CallbackInfo ci) {
        if (player instanceof CustomSkinHolder) {
            Identifier currentSkinId = SkinClient.getCurrentSkinId();
            if (currentSkinId != null) {
                ((CustomSkinHolder) player).setCustomSkin(currentSkinId);
            }
        }
    }
}
