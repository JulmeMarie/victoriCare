package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EContentType {
	ACTIVITY,
	PROJECT,
	ORGANIZATION,
	RELAIS,
	DENTAL,
	DONATION,
	NEWS,
	PARTNER,
	MENTION,
	POST,
	ALL,
	UNKNOWN;

	public static  EContentType get(String type) {
		return Stream.of(EContentType.values())
				.filter(r-> r.name().equalsIgnoreCase(type))
				.findFirst()
				.orElse(EContentType.UNKNOWN);
	}
}
