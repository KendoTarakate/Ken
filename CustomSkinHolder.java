package net.soracraft.soracraftskin.mixin;

import net.minecraft.util.Identifier;

public interface CustomSkinHolder {
    Identifier getCustomSkin();
    void setCustomSkin(Identifier skin);
}
