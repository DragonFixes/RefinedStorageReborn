package com.refinedmods.refinedstorage.common.grid.screen;

import com.refinedmods.refinedstorage.common.Platform;
import com.refinedmods.refinedstorage.common.api.grid.GridSynchronizer;
import com.refinedmods.refinedstorage.common.grid.AbstractGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.NoopGridSynchronizer;
import com.refinedmods.refinedstorage.common.support.widget.AbstractSideButtonWidget;
import com.refinedmods.refinedstorage.common.support.widget.SearchFieldWidget;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createIdentifier;
import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createTranslation;

/**
 * A single side button that cycles through every search box mode: each registered synchronizer
 * (starting with "normal", the no-op synchronizer), first without and then with the search box auto-selected.
 * With JEI present this gives normal, normal (auto-selected), JEI, JEI (auto-selected), JEI two-way and
 * JEI two-way (auto-selected).
 */
class SearchBoxModeSideButtonWidget extends AbstractSideButtonWidget {
    private static final MutableComponent TITLE = createTranslation("gui", "grid.search_box_mode");
    private static final MutableComponent NORMAL = createTranslation("gui", "grid.search_box_mode.normal");
    private static final Identifier AUTO_SELECTED = createIdentifier("widget/side_button/search_box_auto_selected/yes");
    private static final Identifier NOT_AUTO_SELECTED =
        createIdentifier("widget/side_button/search_box_auto_selected/no");

    private final AbstractGridContainerMenu menu;

    SearchBoxModeSideButtonWidget(final AbstractGridContainerMenu menu, final SearchFieldWidget searchField) {
        super(createPressAction(menu, searchField));
        this.menu = menu;
    }

    private static OnPress createPressAction(final AbstractGridContainerMenu menu,
                                             final SearchFieldWidget searchField) {
        return btn -> {
            final boolean autoSelected = !isAutoSelected();
            Platform.INSTANCE.getConfig().setSearchBoxAutoSelected(autoSelected);
            searchField.setAutoSelected(autoSelected);
            if (!autoSelected) {
                menu.toggleSynchronizer();
            }
        };
    }

    private static boolean isAutoSelected() {
        return Platform.INSTANCE.getConfig().isSearchBoxAutoSelected();
    }

    @Override
    protected Identifier getSprite() {
        return isAutoSelected() ? AUTO_SELECTED : NOT_AUTO_SELECTED;
    }

    @Override
    protected MutableComponent getTitle() {
        return TITLE;
    }

    @Override
    protected List<MutableComponent> getSubText() {
        final GridSynchronizer synchronizer = menu.getSynchronizer();
        final MutableComponent mode = synchronizer == NoopGridSynchronizer.INSTANCE
            ? NORMAL.copy()
            : synchronizer.getTitle().copy();
        final MutableComponent text = isAutoSelected()
            ? createTranslation("gui", "grid.search_box_mode.auto_selected", mode)
            : mode;
        return List.of(text.withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected Component getHelpText() {
        return createTranslation("gui", "grid.search_box_mode.help", menu.getSynchronizer().getHelpText());
    }
}
