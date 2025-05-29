package org.acme.domain.model.enums;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AllowedSchemes {
    HTTPS("https"),
    IMAP ("imap"),
    LDAP ("ldap");

    private final String scheme;

    // constructor
    AllowedSchemes(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }

    private static final Map<String,AllowedSchemes> BY_SCHEME =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            e -> e.scheme.toLowerCase(Locale.ROOT),
                            e -> e
                    ));

    public static boolean isAllowed(String raw) {
        if (raw == null) return false;
        return BY_SCHEME.containsKey(raw.toLowerCase(Locale.ROOT));
    }
}