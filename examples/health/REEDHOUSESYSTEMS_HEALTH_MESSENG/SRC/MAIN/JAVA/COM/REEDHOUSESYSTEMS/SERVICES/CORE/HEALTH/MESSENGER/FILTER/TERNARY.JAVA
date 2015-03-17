package com.reedhousesystems.services.core.health.messenger.filter;

/**
 * Used to allow setting true and false in addition to a "use the defaults" neutral. Boxed booleans are not allowed for
 * annotation expressions, hence this class.
 */
public enum Ternary {
    TRUE,
    FALSE,
    NEUTRAL
}
