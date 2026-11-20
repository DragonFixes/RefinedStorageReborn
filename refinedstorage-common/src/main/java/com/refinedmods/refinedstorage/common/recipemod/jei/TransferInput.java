package com.refinedmods.refinedstorage.common.recipemod.jei;

import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import org.jspecify.annotations.Nullable;

record TransferInput(IRecipeSlotView view, TransferInputType type, @Nullable ItemResource autocraftableResource) {
}
