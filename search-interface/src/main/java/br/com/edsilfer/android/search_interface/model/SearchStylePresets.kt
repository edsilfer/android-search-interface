package br.com.edsilfer.android.search_interface.model

import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle

/**
 * Created by efernandes on 11/11/16.
 */

object SearchStylePresets {
    fun lightSearch(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.lightSearchBar(),
                ResultRowPreset.lightResultRow(),
                ResultDisclaimerPreset.lightResultDisclaimer(),
                BackgroundPreset.lightBackground()
        )
    }

    fun darkSearch(): SearchPallet {
        return SearchPallet(
                SearchBarPresets.darkSearchBar(),
                ResultRowPreset.darkResultRow(),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                BackgroundPreset.darkBackground()
        )
    }
}

object SearchBarPresets {
    fun lightSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                R.style.TextInputThemeDark,
                R.color.clr_theme_light_color_primary,
                R.drawable.ic_arrow_left_black_24dp,
                R.drawable.ic_close_black_24dp
        )
    }

    fun darkSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                R.style.TextInputThemeLight,
                R.color.clr_theme_dark_color_primary,
                R.drawable.ic_arrow_left_white_24dp,
                R.drawable.ic_close_white_24dp
        )
    }
}

object ResultRowPreset {
    fun lightResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.SQUARE,
                R.style.TextHeaderThemeDark,
                R.style.TextSubHeader1ThemeDark,
                R.style.TextSubHeader2ThemeDark,
                R.color.clr_theme_light_color_primary
        )
    }

    fun darkResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.SQUARE,
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
                R.style.TextSubHeader1ThemeDark,
                R.color.clr_theme_light_color_primary
        )
    }

    fun darkResultDisclaimer(): SearchPallet.ResultDisclaimer {
        return SearchPallet.ResultDisclaimer(
                R.style.TextSubHeader1ThemeLight,
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