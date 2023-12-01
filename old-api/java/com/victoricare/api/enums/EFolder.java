package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EFolder {
	ACTIVITY("activities"),
	PROJECT("projects"),
	ORGANIZATION("organizations"),
	RELAIS("relais"),
	DENTAL("dentals"),
	DONATION("donations"),
	NEWS("news"),
	PARTNER("partners"),
	MENTION("mentions"),
	POST("posts"),
	CAROUSEL("carousels"),
	USER("users");

	public final String label;

    private EFolder(String label) {
        this.label = label;
    }

    public static EFolder get(String type) {
       return Stream.of(EFolder.values()).filter(
    		   ef-> ef.name().toUpperCase().equals(type.toUpperCase()))
    		   .findAny().orElse(null);
    }
}
