package br.com.edsilfer.android.search_interface.model

import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.enum.InputStyle
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle

/**
 * Created by efernandes on 11/11/16.
 */

object SearchPresets {

    /**
     * Light search with light results row
     */
    fun template01WithCircleResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.lightResultRow(ThumbnailStyle.CIRCLE),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Light search with dark results row
     */
    fun template02WithCircleResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.darkResultRow(ThumbnailStyle.CIRCLE),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Dark search with dark results row
     */
    fun template03WithCircleResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.darkResultRow(ThumbnailStyle.CIRCLE),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Dark search with light results row
     */
    fun template04WithCircleResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.lightResultRow(ThumbnailStyle.CIRCLE),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Light search with light results row
     */
    fun template01WithSquareResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.lightResultRow(ThumbnailStyle.SQUARE),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Light search with dark results row
     */
    fun template02WithSquareResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.darkResultRow(ThumbnailStyle.SQUARE),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Dark search with dark results row
     */
    fun template03WithSquareResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.darkResultRow(ThumbnailStyle.SQUARE),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground(),
                SearchType.SINGLE_SELECT
        )
    }

    /**
     * Dark search with light results row
     */
    fun template04WithSquareResultRow(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.lightResultRow(ThumbnailStyle.SQUARE),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground(),
                SearchType.SINGLE_SELECT
        )
    }
}

object SearchBarPresets {
    fun lightSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                InputStyle.DARK,
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
                InputStyle.LIGHT,
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
    fun lightResultRow(tStyle: ThumbnailStyle): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                tStyle,
                R.style.TextHeaderThemeDark,
                R.style.TextSubHeader1ThemeDark,
                R.style.TextSubHeader2ThemeDark,
                R.color.clr_theme_light_color_primary,
                checkboxColor = R.color.clr_theme_dark_color_primary_dark
        )
    }

    fun darkResultRow(tStyle: ThumbnailStyle): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                tStyle,
                R.style.TextHeaderThemeLight,
                R.style.TextSubHeader1ThemeLight,
                R.style.TextSubHeader2ThemeLight,
                R.color.clr_theme_dark_color_primary,
                checkboxColor = R.color.clr_theme_light_color_primary_dark
        )
    }
}

object ResultDisclaimerPreset {
    fun lightResultDisclaimer(): SearchPallet.ResultDisclaimer {
        return SearchPallet.ResultDisclaimer(
                R.string.str_results_not_found,
                R.style.TextHeaderThemeDark,
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

