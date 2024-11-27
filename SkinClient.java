package net.soracraft.soracraftskin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.soracraft.soracraftskin.mixin.CustomSkinHolder;

public class SkinClient {

    private static Identifier currentSkinId;

    /**
     * Sets the player skin to the provided skin Identifier.
     *
     * @param player The player entity.
     * @param skinId The Identifier of the new skin texture.
     */
    public static void setPlayerSkin(AbstractClientPlayerEntity player, Identifier skinId) {
        if (player instanceof CustomSkinHolder) {
            ((CustomSkinHolder) player).setCustomSkin(skinId);
            currentSkinId = skinId;
        }
    }

    /**
     * Gets the current skin Identifier.
     *
     * @return The Identifier of the current skin.
     */
    public static Identifier getCurrentSkinId() {
        return currentSkinId;
    }
}
