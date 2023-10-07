package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EMediaTypes {
    JPG, JPEG, PNG, MP3, MP4,GIF,PJP,JFIF, PJPEG, UNSUPPORTED;

	public static  EMediaTypes get(String type) {
		return Stream.of(EMediaTypes.values())
				.filter(r-> r.name().equalsIgnoreCase(type))
				.findFirst()
				.orElse(EMediaTypes.UNSUPPORTED);
	}
}
