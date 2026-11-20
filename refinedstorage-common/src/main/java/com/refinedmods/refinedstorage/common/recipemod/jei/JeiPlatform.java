package com.refinedmods.refinedstorage.common.recipemod.jei;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;

import java.util.Optional;

/**
 * Converts the loader-specific fluid ingredient type JEI uses (a NeoForge {@code FluidStack}, a Fabric
 * {@code IJeiFluidIngredient}) to Refined Storage resources.
 */
public interface JeiPlatform {
    Optional<FluidResource> convertJeiIngredientToFluid(Object ingredient);

    Optional<ResourceAmount> convertJeiIngredientToFluidAmount(Object ingredient);
}
