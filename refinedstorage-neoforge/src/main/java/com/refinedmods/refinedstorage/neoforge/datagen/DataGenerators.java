package com.refinedmods.refinedstorage.neoforge.datagen;

import com.refinedmods.refinedstorage.neoforge.datagen.advancement.AdvancementProvider;
import com.refinedmods.refinedstorage.neoforge.datagen.loot.BlockDropsProvider;
import com.refinedmods.refinedstorage.neoforge.datagen.model.ModelProviders;
import com.refinedmods.refinedstorage.neoforge.datagen.recipe.MainRecipeProvider;
import com.refinedmods.refinedstorage.neoforge.datagen.recipe.RecoloringRecipeProvider;
import com.refinedmods.refinedstorage.neoforge.datagen.tag.BlockTagsProvider;
import com.refinedmods.refinedstorage.neoforge.datagen.tag.ItemTagsProvider;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.MOD_ID;

@EventBusSubscriber(modid = MOD_ID)
public class DataGenerators {
    private DataGenerators() {
    }

    /**
     * Loot tables, advancements and recipes are reloadable datapack registries, so their entries are bootstrapped
     * here instead of being written by data providers.
     */
    @SubscribeEvent
    public static void onGatherRegistryEntries(final GatherDataRegistryEntriesEvent e) {
        e.lootTable(new LootTableProvider.SubProviderEntry(BlockDropsProvider::new, LootContextParamSets.BLOCK))
            .advancement(AdvancementProvider::new)
            .recipe(MainRecipeProvider::new)
            .recipe(RecoloringRecipeProvider::new);
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent.Client e) {
        e.createProvider(ModelProviders::new);
        e.createBlockAndItemTags(BlockTagsProvider::new, ItemTagsProvider::new);
    }
}
