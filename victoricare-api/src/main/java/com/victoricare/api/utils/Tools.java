package com.victoricare.api.utils;

import java.util.List;

import jakarta.annotation.Nullable;

public abstract class Tools {

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * @param str
     * @return false if string is null or contains no character
     */
    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    /**
     * @param str
     * @return false if string is null or contains no character
     */
    public static boolean isNotUnknown(String str) {
        return str == null || !str.equalsIgnoreCase("unknown");
    }

    /**
     * @param <T>
     * @param list
     * @return false if list is null or contains no element
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        return !isNullOrEmpty(list);
    }

    public static <T> boolean isUnique(List<T> list) {
        return list != null && list.size() == 1;
    }

    /*
     * Allow to calculate size of a list
     * Return -1 is the list is null
     * No exception will be thrown
     */
    public static <T> Integer size(List<T> list) {
        return list == null ? -1 : list.size();
    }

    /*
     * Allow to calculate length of a String
     * Return -1 is the list is null
     * No exception will be thrown
     */
    public static <T> Integer length(String str) {
        return str == null ? -1 : str.length();
    }

    public static <T> boolean isEqual(@Nullable T oneObject, @Nullable T otherObject) {
        if (oneObject == null || otherObject == null)
            return false;
        return oneObject.equals(otherObject);
    }

}
