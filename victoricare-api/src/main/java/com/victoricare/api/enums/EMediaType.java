package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EMediaType {
	AUDIO, VIDEO, IMAGE, UNSUPPORTED;

	public static EMediaType get(String type) {
		return Stream.of(EMediaType.values())
				.filter(r -> r.name().equalsIgnoreCase(type))
				.findFirst()
				.orElse(EMediaType.UNSUPPORTED);
	}
}
