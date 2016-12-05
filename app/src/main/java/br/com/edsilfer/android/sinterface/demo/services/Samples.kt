package br.com.edsilfer.android.sinterface.demo.services

import br.com.edsilfer.android.search_interface.R
import br.com.edsilfer.android.search_interface.model.ResultDisclaimerPreset
import br.com.edsilfer.android.search_interface.model.SearchPallet
import br.com.edsilfer.android.search_interface.model.enum.InputStyle
import br.com.edsilfer.android.search_interface.model.enum.SearchType
import br.com.edsilfer.android.search_interface.model.enum.ThumbnailStyle

/**
 * Created by efernandes on 22/11/16.
 */

class Samples {
    fun sample01(): SearchPallet {
        return SearchPallet(
                SearchPallet.SearchBar(
                        InputStyle.LIGHT,
                        R.color.clr_theme_dark_color_primary,
                        R.color.clr_theme_dark_color_primary_dark,
                        android.R.color.holo_red_dark,
                        R.drawable.ic_arrow_left_white_24dp,
                        R.drawable.ic_close_white_24dp,
                        R.string.str_search_bar_default_hint
                ),
                SearchPallet.ResultRow(
                        ThumbnailStyle.SQUARE,
                        R.style.TextHeaderThemeLight,
                        R.style.TextSubHeader1ThemeLight,
                        R.style.TextSubHeader2ThemeLight,
                        R.color.clr_theme_dark_color_primary,
                        alpha = 0.4f,
                        checkboxColor = br.com.edsilfer.android.sinterface.demo.R.color.clr_sample01_result_row_checkbox
                ),
                ResultDisclaimerPreset.darkResultDisclaimer(),
                SearchPallet.Background(
                        color = R.color.clr_theme_dark_color_primary,
                        drawable = br.com.edsilfer.android.sinterface.demo.R.drawable.sample_background_01
                ),
                SearchType.MULTI_SELECT
        )
    }
}
