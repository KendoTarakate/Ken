package net.soracraft.soracraftskin;

import net.minecraft.util.Identifier;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;

public class SkinLoader {

    /**
     * Downloads a skin from the given URL and saves it as a local file.
     * If a skin with the same hash already exists, it reuses the cached file.
     *
     * @param skinUrl The URL of the skin to download.
     * @return The cached or newly downloaded skin file.
     * @throws IOException If there's an issue with downloading or saving the file.
     */
    public static File LoaderSkin(String skinUrl) throws IOException {
        // Validate the URL
        if (skinUrl == null || !skinUrl.startsWith("http")) {
            throw new IllegalArgumentException("Invalid URL: " + skinUrl);
        }

        // Create a cache directory if it doesn't exist
        File cacheDir = new File("skins_cache");
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new IOException("Failed to create cache directory");
        }

        // Compute the hash of the URL
        String hash = computeHash(skinUrl);
        File cachedFile = new File(cacheDir, "skin_" + hash + ".png");

        // Check if the file already exists
        if (cachedFile.exists()) {
            System.out.println("Skin already cached: " + cachedFile.getAbsolutePath());
            return cachedFile; // Return the cached file
        }

        // Download and save the file if it doesn't exist
        try (InputStream inputStream = new URL(skinUrl).openStream();
             FileOutputStream outputStream = new FileOutputStream(cachedFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new IOException("Failed to download skin: " + e.getMessage(), e);
        }

        System.out.println("Downloaded and cached new skin: " + cachedFile.getAbsolutePath());
        return cachedFile;
    }

    /**
     * Processes and applies a skin by returning an Identifier for the texture.
     *
     * @param skinFile  The file of the skin to apply.
     * @param playerName The name of the player to associate with the skin.
     * @return An Identifier pointing to the skin texture.
     */
    public static Identifier applySkin(File skinFile, String playerName) {
        // Assume this saves the skin and returns a texture Identifier
        return new Identifier("soracraftskin", "skins/" + playerName + "_" + skinFile.getName());
    }

    /**
     * Computes a hash for a given input string (e.g., a URL).
     *
     * @param input The input string to hash.
     * @return A hexadecimal representation of the hash.
     */
    private static String computeHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

            // Convert bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute hash: " + e.getMessage(), e);
        }
    }
}
