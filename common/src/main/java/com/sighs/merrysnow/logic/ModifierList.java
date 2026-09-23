package com.sighs.merrysnow.logic;

import java.util.List;

/**
 * Resolves the {@code "minecraft:forest=snow"} style entries of the biome modifier lists.
 *
 * <p>The lists are kept as raw strings by every target so the parsing stays loader independent.
 */
public final class ModifierList {
    private ModifierList() {
    }

    /**
     * Returns the configured value for {@code biomeId}, or {@link ConfigDefaults#DEFAULT} when the
     * id is not listed or the entry is malformed.
     */
    public static String lookup(List<String> entries, String biomeId) {
        if (entries == null || biomeId == null) {
            return ConfigDefaults.DEFAULT;
        }
        for (String entry : entries) {
            if (entry == null) {
                continue;
            }
            String[] parts = entry.split("=");
            if (parts.length >= 2 && parts[0].equals(biomeId)) {
                return parts[1];
            }
        }
        return ConfigDefaults.DEFAULT;
    }
}
