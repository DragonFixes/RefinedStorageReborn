package com.refinedmods.refinedstorage.common.recipemod.jei;

import com.refinedmods.refinedstorage.common.content.Items;
import com.refinedmods.refinedstorage.common.grid.CraftingGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;

import java.util.function.Supplier;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IIngredientAliasRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.MOD_ID;
import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createIdentifier;

/**
 * Discovered by JEI through the {@link JeiPlugin} annotation on NeoForge and through the {@code jei_mod_plugin}
 * entrypoint on Fabric. Every registration is skipped while {@link JeiIntegration} is inactive.
 */
@JeiPlugin
public class RefinedStorageModPlugin implements IModPlugin {
    private static final Identifier ID = createIdentifier("plugin");

    @Nullable
    private static IJeiRuntime runtime;
    @Nullable
    private static IPlatformFluidHelper<?> fluidHelper;

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerIngredientAliases(final IIngredientAliasRegistration registration) {
        if (!JeiIntegration.isActive()) {
            return;
        }
        registration.addAliases(VanillaTypes.ITEM_STACK, Items.INSTANCE.getDiskInterfaces()
            .stream()
            .map(Supplier::get)
            .map(Item::getDefaultInstance)
            .toList(), "alias.%s.jei.refinedstorage1_disk_manipulator".formatted(MOD_ID));
    }

    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {
        if (!JeiIntegration.isActive()) {
            return;
        }
        registration.addRecipeTransferHandler(new CraftingGridRecipeTransferHandler<>(
            CraftingGridContainerMenu.class
        ), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(new PatternGridCraftingRecipeTransferHandler(), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(
            new PatternGridStonecutterRecipeTransferHandler(),
            RecipeTypes.STONECUTTING
        );
        registration.addRecipeTransferHandler(
            new PatternGridSmithingTableRecipeTransferHandler(),
            RecipeTypes.SMITHING
        );
        registration.addUniversalRecipeTransferHandler(new PatternGridProcessingRecipeTransferHandler());
    }

    @Override
    public void onRuntimeAvailable(final IJeiRuntime newRuntime) {
        RefinedStorageModPlugin.runtime = newRuntime;
    }

    @Override
    public void onRuntimeUnavailable() {
        RefinedStorageModPlugin.runtime = null;
    }

    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {
        if (!JeiIntegration.isActive()) {
            return;
        }
        RefinedStorageModPlugin.fluidHelper = registration.getJeiHelpers().getPlatformFluidHelper();
        registration.addGenericGuiContainerHandler(
            AbstractBaseScreen.class,
            new ResourceGuiContainerHandler(registration.getJeiHelpers().getIngredientManager())
        );
        registration.addGenericGuiContainerHandler(
            AbstractGridScreen.class,
            new GridGuiContainerHandler(registration.getJeiHelpers().getIngredientManager())
        );
        registration.addGenericGuiContainerHandler(
            AbstractBaseScreen.class,
            new ExclusionZonesGuiContainerHandler()
        );
        registration.addGhostIngredientHandler(AbstractBaseScreen.class, new ResourceGhostIngredientHandler());
        registration.addGhostIngredientHandler(AbstractBaseScreen.class, new FilterGhostIngredientHandler());
    }

    @Nullable
    static IJeiRuntime getRuntime() {
        return runtime;
    }

    @Nullable
    static IPlatformFluidHelper<?> getFluidHelper() {
        return fluidHelper;
    }
}
