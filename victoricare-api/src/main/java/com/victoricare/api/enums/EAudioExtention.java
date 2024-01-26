package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EAudioExtention {
	MP3, UNSUPPORTED;

	public static EAudioExtention get(String period) {
		return Stream.of(EAudioExtention.values())
				.filter(r -> r.name().equalsIgnoreCase(period))
				.findFirst()
				.orElse(EAudioExtention.UNSUPPORTED);
	}
}
