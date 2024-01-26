package com.victoricare.api.enums;

public enum EPattern {
    // 1. Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or
    // uppercase.
    // 2. Username allowed of the dot (.), underscore (_), and hyphen (-).
    // 3. The dot (.), underscore (_), or hyphen (-) must not be the first or last
    // character.
    // 4. The dot (.), underscore (_), or hyphen (-) does not appear consecutively,
    // e.g., java..regex
    // 5. The number of characters must be between 3 to 15.
    USERNAME("^[a-zA-ZÀ-ÿ0-9]([._-](?![._-])|[a-zA-ZÀ-ÿ0-9]){1,13}[a-zA-ZÀ-ÿ0-9]$"),

    // 1. Name consists of alphanumeric characters (a-zA-ZÀ-ÿ0-9), lowercase, or
    // uppercase.
    // 2. Name allowed of the dot (.), underscore (_), and hyphen (-).
    // 3. The dot (.), underscore (_), or hyphen (-) must not be the first or last
    // character.
    // 4. The dot (.), underscore (_), or hyphen (-) does not appear consecutively,
    // e.g., java..regex
    // 5. The number of characters must be between 3 to 25.
    NAME("^[a-zA-ZÀ-ÿ0-9]([a-zA-ZÀ-ÿ0-9 '-']){1,23}[a-zA-ZÀ-ÿ0-9]$"),

    CODE_6DIGIT("^[0-9]{6,6}$"),

    /**
     * ^ # début de la chaîne de caractères
     * (?=.*[0-9]) # un chiffre doit apparaître au moins une fois
     * (?=.*[a-z]) # une lettre minuscule doit apparaître au moins une fois
     * (?=.*[A-Z]) # une lettre majuscule doit apparaître au moins une fois
     * (?=.*[@#$%^&+=]) # un caractère spécial doit apparaître au moins une fois
     * (?=\S+$) # aucun espace blanc n'est autorisé dans la chaîne entière
     * .{6,} # n'importe quoi, au moins 6 caractères
     * $ # fin de la chaîne de caractères
     */
    PASS("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"),

    EMAIL("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

    public final String value;

    private EPattern(String value) {
        this.value = value;
    }
}
