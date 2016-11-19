package br.com.edsilfer.android.search_interface.model

import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle

/**
 * Created by efernandes on 11/11/16.
 */

object SearchStylePresets {

    /**
     * Light search with light results row
     */
    fun template01(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.lightResultRow(),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground()
        )
    }

    /**
     * Light search with dark results row
     */
    fun template02(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.darkResultRow(),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground()
        )
    }

    /**
     * Dark search with dark results row
     */
    fun template03(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.darkResultRow(),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground()
        )
    }

    /**
     * Dark search with light results row
     */
    fun template04(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.lightResultRow(),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground()
        )
    }
}

object SearchBarPresets {
    fun lightSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                R.style.TextInputThemeLight,
                R.style.TextInputLayoutThemeLight,
                R.color.clr_theme_light_color_primary,
                R.color.clr_theme_light_color_primary_dark,
                android.R.color.black,
                R.drawable.ic_arrow_left_black_24dp,
                R.drawable.ic_close_black_24dp,
                R.string.str_search_bar_default_hint
        )
    }

    fun darkSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                R.style.TextInputThemeDark,
                R.style.TextInputLayoutThemeDark,
                R.color.clr_theme_dark_color_primary,
                R.color.clr_theme_dark_color_primary_dark,
                android.R.color.white,
                R.drawable.ic_arrow_left_white_24dp,
                R.drawable.ic_close_white_24dp,
                R.string.str_search_bar_default_hint
        )
    }
}

object ResultRowPreset {
    fun lightResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.CIRCLE,
                R.style.TextHeaderThemeDark,
                R.style.TextSubHeader1ThemeDark,
                R.style.TextSubHeader2ThemeDark,
                R.color.clr_theme_light_color_primary
        )
    }

    fun darkResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.CIRCLE,
                R.style.TextHeaderThemeLight,
                R.style.TextSubHeader1ThemeLight,
                R.style.TextSubHeader2ThemeLight,
                R.color.clr_theme_dark_color_primary
        )
    }
}

object ResultDisclaimerPreset {
    fun lightResultDisclaimer(): SearchPallet.ResultDisclaimer {
        return SearchPallet.ResultDisclaimer(
                R.string.str_results_not_found,
                R.style.TextSubHeader1ThemeLight,
                R.color.clr_theme_light_color_primary
        )
    }

    fun darkResultDisclaimer(): SearchPallet.ResultDisclaimer {
        return SearchPallet.ResultDisclaimer(
                R.string.str_results_not_found,
                R.style.TextHeaderThemeLight,
                R.color.clr_theme_dark_color_primary
        )
    }
}

object BackgroundPreset {
    fun lightBackground(): SearchPallet.Background {
        return SearchPallet.Background(
                color = R.color.clr_theme_light_color_primary
        )
    }

    fun darkBackground(): SearchPallet.Background {
        return SearchPallet.Background(
                color = R.color.clr_theme_dark_color_primary
        )
    }
}

