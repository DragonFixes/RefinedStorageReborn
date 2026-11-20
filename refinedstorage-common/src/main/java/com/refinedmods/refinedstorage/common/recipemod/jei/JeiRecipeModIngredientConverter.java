package com.refinedmods.refinedstorage.common.recipemod.jei;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.api.support.resource.PlatformResourceKey;
import com.refinedmods.refinedstorage.common.api.support.resource.RecipeModIngredientConverter;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.Optional;

import mezz.jei.api.helpers.IPlatformFluidHelper;
import net.minecraft.world.item.ItemStack;

class JeiRecipeModIngredientConverter implements RecipeModIngredientConverter {
    @Override
    public Optional<PlatformResourceKey> convertToResource(final Object ingredient) {
        final var fluid = JeiIntegration.getPlatform().convertJeiIngredientToFluid(ingredient);
        if (fluid.isPresent()) {
            return fluid.map(f -> f);
        }
        if (ingredient instanceof ItemStack itemStack) {
            return Optional.of(ItemResource.ofItemStack(itemStack));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ResourceAmount> convertToResourceAmount(final Object ingredient) {
        final var fluid = JeiIntegration.getPlatform().convertJeiIngredientToFluidAmount(ingredient);
        if (fluid.isPresent()) {
            return fluid;
        }
        if (ingredient instanceof ItemStack itemStack) {
            return Optional.of(new ResourceAmount(ItemResource.ofItemStack(itemStack), itemStack.getCount()));
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public Optional<Object> convertToIngredient(final PlatformResourceKey resource) {
        if (resource instanceof ItemResource itemResource) {
            return Optional.of(itemResource.toItemStack());
        }
        final IPlatformFluidHelper<?> fluidHelper = RefinedStorageModPlugin.getFluidHelper();
        if (resource instanceof FluidResource fluidResource && fluidHelper != null) {
            return Optional.of(fluidHelper.create(
                fluidResource.fluid().builtInRegistryHolder(),
                fluidHelper.bucketVolume(),
                fluidResource.components()
            ));
        }
        return Optional.empty();
    }
}
