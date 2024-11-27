package net.soracraft.soracraftskin;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;

public class SkinCommand implements ModInitializer {

    @Override
    public void onInitialize() {
        register();
    }

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("skin")
                    .then(CommandManager.literal("url")
                            .then(CommandManager.argument("skinurl", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        String skinUrl = StringArgumentType.getString(context, "skinurl");
                                        ServerCommandSource source = context.getSource();

                                        try {
                                            source.sendFeedback(() -> Text.literal("Downloading skin: " + skinUrl), false);

                                            // Download and cache the skin
                                            File downloadedSkin = SkinLoader.LoaderSkin(skinUrl);
                                            source.sendFeedback(() -> Text.literal("Skin downloaded and cached at: " + downloadedSkin.getAbsolutePath()), false);

                                            // Apply the skin to the player
                                            ServerPlayerEntity player = source.getPlayer();
                                            if (player != null) {
                                                Identifier skinIdentifier = SkinLoader.applySkin(downloadedSkin, player.getName().getString());
                                                SkinClient.setPlayerSkin(player, skinIdentifier);

                                                source.sendFeedback(() -> Text.literal("Skin applied successfully."), false);
                                            }
                                        } catch (Exception e) {
                                            source.sendError(Text.literal("Failed to apply skin: " + e.getMessage()));
                                        }

                                        return 1; // Success
                                    }))));
        });
    }
}
