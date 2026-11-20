package com.refinedmods.refinedstorage.common.recipemod.jei;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;

import org.jspecify.annotations.Nullable;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createIdentifier;
import static java.util.Objects.requireNonNull;

/**
 * Entry point of the JEI integration. Loaders call {@link #init(RefinedStorageApi, JeiPlatform)} only when JEI is
 * installed, so that no JEI class is resolved otherwise.
 */
public final class JeiIntegration {
    public static final String MOD_ID = "jei";
    /**
     * The standalone "Refined Storage - JEI Integration" addon registers the same handlers and synchronizers,
     * so the built-in integration stays inactive when it is installed.
     */
    public static final String ADDON_MOD_ID = "refinedstorage_jei_integration";

    @Nullable
    private static JeiPlatform platform;

    private JeiIntegration() {
    }

    public static void init(final RefinedStorageApi api, final JeiPlatform jeiPlatform) {
        JeiIntegration.platform = jeiPlatform;
        api.addIngredientConverter(new JeiRecipeModIngredientConverter());
        api.getGridSynchronizerRegistry().register(createIdentifier("jei"), new JeiGridSynchronizer(false));
        api.getGridSynchronizerRegistry().register(createIdentifier("jei_two_way"), new JeiGridSynchronizer(true));
    }

    static boolean isActive() {
        return platform != null;
    }

    static JeiPlatform getPlatform() {
        return requireNonNull(platform, "JEI integration isn't initialized");
    }
}
