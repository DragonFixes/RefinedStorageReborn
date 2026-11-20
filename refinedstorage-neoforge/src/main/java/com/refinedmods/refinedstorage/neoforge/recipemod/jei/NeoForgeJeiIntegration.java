package com.refinedmods.refinedstorage.neoforge.recipemod.jei;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.recipemod.jei.JeiIntegration;

import net.neoforged.fml.ModList;

/**
 * Only {@link JeiIntegration} names JEI types, and it is touched only once JEI is known to be loaded, so this class
 * is safe to load without JEI installed.
 */
public final class NeoForgeJeiIntegration {
    private NeoForgeJeiIntegration() {
    }

    public static void initIfLoaded(final RefinedStorageApi api) {
        final ModList modList = ModList.get();
        if (!modList.isLoaded(JeiIntegration.MOD_ID) || modList.isLoaded(JeiIntegration.ADDON_MOD_ID)) {
            return;
        }
        JeiIntegration.init(api, new NeoForgeJeiPlatform());
    }
}
