package ay2021s1_cs2103_w16_3.finesse.ui;

import static java.util.Objects.requireNonNull;

import ay2021s1_cs2103_w16_3.finesse.commons.core.index.Index;

/**
 * Represents the state of the current UI.
 */
public class UiState {
    /**
     * Tabs present in the UI.
     */
    public enum Tab {
        OVERVIEW(1),
        INCOME(2),
        EXPENSES(3),
        ANALYTICS(4),
        USER_GUIDE(5);

        /** The index of the tab in the {@code TabPane}. */
        private final Index tabIndex;

        /**
         * Constructs a new {@code Tab} enum with the specified tab index.
         *
         * @param tabIndex The index of the tab in the {@code TabPane}.
         */
        Tab(int tabIndex) {
            this.tabIndex = Index.fromOneBased(tabIndex);
        }

        /**
         * Returns the index of the tab in the {@code TabPane}.
         *
         * @return The index of the tab in the {@code TabPane}.
         */
        public Index getTabIndex() {
            return tabIndex;
        }

        /**
         * Returns a string representation of this {@code Tab}.
         *
         * @return A string representation of this {@code Tab}.
         */
        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    /**
     * The current selected tab.
     */
    private Tab currentTab;

    /**
     * Creates a {@code UiState} with the currently selected tab set to the 'Overview' tab.
     */
    public UiState() {
        currentTab = Tab.OVERVIEW;
    }

    /**
     * Creates a {@code UiState} with the specified currently selected tab.
     *
     * @param currentTab The currently selected tab.
     */
    public UiState(Tab currentTab) {
        requireNonNull(currentTab);
        this.currentTab = currentTab;
    }

    /**
     * Returns the currently selected tab.
     *
     * @return The currently selected tab.
     */
    public Tab getCurrentTab() {
        return currentTab;
    }

    /**
     * Updates the currently selected tab.
     *
     * @param currentTab The currently selected tab.
     */
    public void setCurrentTab(Tab currentTab) {
        requireNonNull(currentTab);
        this.currentTab = currentTab;
    }
}
