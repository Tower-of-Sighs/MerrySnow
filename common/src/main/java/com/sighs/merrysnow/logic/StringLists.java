package com.sighs.merrysnow.logic;

import java.util.List;

/** Small string helpers shared by the per-target command implementations. */
public final class StringLists {
    private StringLists() {
    }

    /** Renders the given values as a JSON string array, used for the copy-to-clipboard payloads. */
    public static String toJsonArray(List<String> items) {
        if (items == null || items.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\"").append(items.get(i)).append("\"");
        }
        sb.append("]");
        return sb.toString();
    }
}
