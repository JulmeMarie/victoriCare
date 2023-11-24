package com.victoricare.api.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ERight {

	SUPERADMINISTRATOR, ADMINISTRATOR, MODERATOR, USER, ANONYMOUS;

	public static ERight get(String right) {
		return Stream.of(ERight.values())
				.filter(r -> r.name().equalsIgnoreCase(right))
				.findFirst()
				.orElse(ERight.ANONYMOUS);
	}

	public static List<ERight> compute(ERight right) {
		String rightName = right.name();
		return Stream.of(ERight.values()).filter(r -> {
			String rName = r.name();
			if (rName.equals(rightName) || rightName.equals(ERight.ADMINISTRATOR.name())
					|| (!rName.equals(ERight.ADMINISTRATOR.name()) && rightName.equals(ERight.MODERATOR.name())))
				return true;
			if (rName.equals(ERight.ANONYMOUS.name()) && rightName.equals(ERight.USER.name()))
				return true;
			return false;
		}).collect(Collectors.toList());
	}

	public static boolean isAtLeastAdministrator(String right) {
		return compute(ERight.get(right)).contains(ERight.ADMINISTRATOR);
	}

	public static boolean isAtLeastModerator(String right) {
		return compute(ERight.get(right)).contains(ERight.MODERATOR);
	}
}
