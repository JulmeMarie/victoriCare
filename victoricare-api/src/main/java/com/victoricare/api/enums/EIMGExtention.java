package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EIMGExtention {
	JPG, JPEG, PNG, GIF, PJP, JFIF, PJPEG, UNSUPPORTED;

	public static EIMGExtention get(String extention) {
		return Stream.of(EIMGExtention.values())
				.filter(r -> r.name().equalsIgnoreCase(extention))
				.findFirst()
				.orElse(EIMGExtention.UNSUPPORTED);
	}
}
