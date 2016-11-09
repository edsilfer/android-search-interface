package br.com.edsilfer.android.sinterface.demo.model.enum

/**
 * Created by efernandes on 10/24/16.
 */

enum class MessageDirection(value: String) {
    INCOME("income"), OUTGOING("outgoing");

    private var mValue: String = value

    override fun toString(): String {
        return mValue
    }

    fun fromString(value: String): MessageDirection {
        for (v in values()) {
            if (v.equals(value)) {
                return v
            }
        }
        return INCOME
    }
}

