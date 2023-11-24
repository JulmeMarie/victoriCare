package com.victoricare.api.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ERole {

	PRESIDENT, SECRETARY, TRESURER, TEAM, MEMBER, ALL, NONE, PARENT, BABYSITTER, USER;

	public static ERole get(String role) {
		return Stream.of(ERole.values())
				.filter(r -> r.name().equalsIgnoreCase(role))
				.findFirst()
				.orElse(ERole.NONE);
	}

	/**
	 * Allows to have team roles list
	 * 
	 * @param role
	 * @return
	 */
	public static List<ERole> compute(ERole role) {
		return Stream.of(ERole.values()).filter(r -> {
			if (r.equals(role) || role.equals(ERole.ALL)
					|| (!(r.equals(ERole.NONE) || r.equals(ERole.MEMBER) || r.equals(ERole.ALL))
							&& role.equals(ERole.TEAM)))
				return true;
			return false;
		}).collect(Collectors.toList());
	}
}
