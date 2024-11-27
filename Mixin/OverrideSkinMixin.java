package net.soracraft.soracraftskin.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractClientPlayerEntity.class)
public class OverrideSkinMixin {

    /**
     * Overrides the player's skin texture with the custom skin if available.
     */
    @Overwrite
    public Identifier getSkinTexture() {
        if (this instanceof CustomSkinHolder) {
            Identifier customSkin = ((CustomSkinHolder) this).getCustomSkin();
            if (customSkin != null) {
                return customSkin; // Return the custom skin
            }
        }

        // Fallback to the default skin texture
        return this.getSkinTexture();
    }
}
