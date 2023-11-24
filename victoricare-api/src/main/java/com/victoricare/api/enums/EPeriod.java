package com.victoricare.api.enums;

import java.util.stream.Stream;

public enum EPeriod {
	TODAY,
	WEEK,
	MONTH,
	YEAR,
	UNKNOWN;

	public static EPeriod get(String period) {
		return Stream.of(EPeriod.values())
				.filter(r -> r.name().equalsIgnoreCase(period))
				.findFirst()
				.orElse(EPeriod.UNKNOWN);
	}
}
