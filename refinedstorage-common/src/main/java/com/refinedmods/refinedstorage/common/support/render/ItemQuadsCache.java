package com.refinedmods.refinedstorage.common.support.render;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.ItemQuads;

/**
 * Splits baked quad lists into {@link ItemQuads} once and reuses the result.
 * Item models rebuild their render state every frame, and {@link ItemQuads#split(List)} allocates, so models keep one
 * cache per instance. Keys are compared by identity: the lists are the immutable quad lists baked with the model,
 * and a resource reload creates a new model (and cache) for new lists.
 */
public final class ItemQuadsCache {
    private final Map<List<BakedQuad>, ItemQuads> single = new IdentityHashMap<>();
    private final Map<List<BakedQuad>, Map<List<BakedQuad>, ItemQuads>> pairs = new IdentityHashMap<>();

    public synchronized ItemQuads get(final List<BakedQuad> quads) {
        return single.computeIfAbsent(quads, ItemQuads::split);
    }

    public synchronized ItemQuads get(final List<BakedQuad> first, final List<BakedQuad> second) {
        return pairs.computeIfAbsent(first, k -> new IdentityHashMap<>()).computeIfAbsent(second, k -> {
            final List<BakedQuad> combined = new ArrayList<>(first.size() + second.size());
            combined.addAll(first);
            combined.addAll(second);
            return ItemQuads.split(combined);
        });
    }
}
