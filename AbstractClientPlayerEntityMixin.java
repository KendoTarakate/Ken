package net.soracraft.soracraftskin.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin implements CustomSkinHolder {
    @Unique
    private Identifier customSkin = null;

    @Override
    public Identifier getCustomSkin() {
        return customSkin;
    }

    @Override
    public void setCustomSkin(Identifier skin) {
        this.customSkin = skin;
    }
}
