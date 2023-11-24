package com.victoricare.api.validators;

import java.util.regex.Pattern;
import com.victoricare.api.enums.EPattern;

public class Validator {

    public static boolean checkNullableString(EPattern pattern, String str) {
        if (str == null || str.isBlank()) {
            return true;
        }
        return compile(pattern, str);
    }

    public static boolean checkNonNullableString(EPattern pattern, String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        return compile(pattern, str);
    }

    public static boolean checNullableInteger(Integer number) {
        return number == null || (number >= 0 && number <= Integer.MAX_VALUE);
    }

    public static boolean checkNonNullableInteger(Integer number) {
        return number != null && number >= 0 && number <= Integer.MAX_VALUE;
    }

    private static boolean compile(EPattern pattern, String email) {
        return Pattern.compile(pattern.value).matcher(email).matches();
    }

    public static boolean checkNonNullableInteger(EPattern pattern, Integer code) {
        return code != null && compile(pattern, code.toString());
    }
}
