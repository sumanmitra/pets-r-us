package com.petsrus.service;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Colors {
	BLACK, BROWN, WHITE, CREAM;

	@JsonCreator
	public static Colors fromValue(String value) {
		return getEnumFromString(Colors.class, value);
	}

	private static Colors getEnumFromString(Class<Colors> enumClass, String value) {

		for (Enum<?> constant : enumClass.getEnumConstants()) {
			if (constant.toString().equalsIgnoreCase(value)) {
				return (Colors) constant;
			}
		}
		StringBuilder validValues = new StringBuilder();
		for (Enum<?> constant : enumClass.getEnumConstants()) {
			
			validValues.append(constant+" ");
		}
		throw new IllegalArgumentException("Valid Values :"+validValues.toString());
	}

}
