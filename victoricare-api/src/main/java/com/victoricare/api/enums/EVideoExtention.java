package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EVideoExtention {
	MP4, UNSUPPORTED;

	public static EVideoExtention get(String period) {
		return Stream.of(EVideoExtention.values())
				.filter(r -> r.name().equalsIgnoreCase(period))
				.findFirst()
				.orElse(EVideoExtention.UNSUPPORTED);
	}
}
