package br.com.edsilfer.android.search_interface.model

import android.graphics.Typeface
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
                TextPreset.darkEditText(),
                R.color.clr_theme_light_color_primary,
                R.color.clr_theme_light_color_primary_dark,
                R.drawable.ic_arrow_left_black_24dp,
                R.drawable.ic_close_black_24dp
        )
    }

    fun darkSearchBar(): SearchPallet.SearchBar {
        return SearchPallet.SearchBar(
                TextPreset.lightEditText(),
                R.color.clr_theme_dark_color_primary,
                R.color.clr_theme_dark_color_primary_dark,
                R.drawable.ic_arrow_left_white_24dp,
                R.drawable.ic_close_white_24dp
        )
    }
}

object ResultRowPreset {
    fun lightResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.SQUARE,
                TextPreset.darkHeader(),
                TextPreset.darkSubHeader1(),
                TextPreset.darkSubHeader2(),
                R.color.clr_theme_light_color_primary
        )
    }

    fun darkResultRow(): SearchPallet.ResultRow {
        return SearchPallet.ResultRow(
                ThumbnailStyle.SQUARE,
                TextPreset.lightHeader(),
                TextPreset.lightSubHeader1(),
                TextPreset.lightSubHeader2(),
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

object TextPreset {
    fun darkEditText(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_dark_text_input,
                R.color.clr_theme_dark_text_input_hint,
                R.dimen.dim_presets_commons_text_input_size,
                Typeface.NORMAL,
                R.color.clr_theme_dark_text_input,
                "sans-serif"
        )
    }

    fun darkHeader(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_dark_text_input,
                R.color.clr_theme_dark_text_input_hint,
                R.dimen.dim_presets_commons_text_header_size,
                Typeface.NORMAL,
                R.color.clr_theme_dark_text_input,
                "sans-serif"
        )
    }

    fun darkSubHeader1(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_dark_text_input,
                R.color.clr_theme_dark_text_input_hint,
                R.dimen.dim_presets_commons_text_subheader1_size,
                Typeface.NORMAL,
                R.color.clr_theme_dark_text_input,
                "sans-serif"
        )
    }

    fun darkSubHeader2(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_light_text_input,
                R.color.clr_theme_light_text_input_hint,
                R.dimen.dim_presets_commons_text_subheader2_size,
                Typeface.NORMAL,
                R.color.clr_theme_light_text_input,
                "sans-serif"
        )
    }

    fun lightEditText(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_light_text_input,
                R.color.clr_theme_light_text_input_hint,
                R.dimen.dim_presets_commons_text_input_size,
                Typeface.NORMAL,
                R.color.clr_theme_light_text_input,
                "sans-serif"
        )
    }

    fun lightHeader(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_light_text_input,
                R.color.clr_theme_light_text_input_hint,
                R.dimen.dim_presets_commons_text_header_size,
                Typeface.NORMAL,
                R.color.clr_theme_light_text_input,
                "sans-serif"
        )
    }

    fun lightSubHeader1(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_light_text_input,
                R.color.clr_theme_light_text_input_hint,
                R.dimen.dim_presets_commons_text_subheader1_size,
                Typeface.NORMAL,
                R.color.clr_theme_light_text_input,
                "sans-serif"
        )
    }

    fun lightSubHeader2(): SearchPallet.TextStyle {
        return SearchPallet.TextStyle(
                R.color.clr_theme_light_text_input,
                R.color.clr_theme_light_text_input_hint,
                R.dimen.dim_presets_commons_text_subheader2_size,
                Typeface.NORMAL,
                R.color.clr_theme_light_text_input,
                "sans-serif"
        )
    }
}
