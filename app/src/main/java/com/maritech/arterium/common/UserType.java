package com.maritech.arterium.common;

import org.jetbrains.annotations.NotNull;

public enum UserType {
        DOCTOR("doctor"),
        MEDICAL("medical"),
        REGIONAL("manager");

        private final String type;

        UserType(String stringVal) {
            type = stringVal;
        }

        @NotNull
        public String toString() {
            return type;
        }

        public static String getEnumByString(String code) {
            for (UserType e : UserType.values()) {
                if (e.name().equals(code)) return e.name();
            }
            return null;
        }
    }