package org.acme.domain.model.enums;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AllowedSchemes {
    HTTPS("https"),
    IMAP ("imaps"),
    LDAP ("ldap");

    private final String scheme;

    // constructor
    AllowedSchemes(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }

    // Generates a final hashmap of the enum values
    private static final Map<String,AllowedSchemes> BY_SCHEME =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            e -> e.scheme.toLowerCase(Locale.ROOT), // Locale.ROOT disregards local machine settings and uses java standards
                            e -> e
                    ));

    // Returns false if the scheme is null or not in the BY_SCHEME hashmap
    public static boolean isAllowed(String raw) {
        if (raw == null) return false;
        return BY_SCHEME.containsKey(raw.toLowerCase(Locale.ROOT));
    }
}