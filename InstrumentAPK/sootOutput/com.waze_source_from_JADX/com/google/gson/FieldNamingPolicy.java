package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy {
    IDENTITY {
        public String translateName(Field $r1) throws  {
            return $r1.getName();
        }
    },
    UPPER_CAMEL_CASE {
        public String translateName(Field $r1) throws  {
            return FieldNamingPolicy.upperCaseFirstLetter($r1.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES {
        public String translateName(Field $r1) throws  {
            return FieldNamingPolicy.upperCaseFirstLetter(FieldNamingPolicy.separateCamelCase($r1.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES {
        public String translateName(Field $r1) throws  {
            return FieldNamingPolicy.separateCamelCase($r1.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    },
    LOWER_CASE_WITH_DASHES {
        public String translateName(Field $r1) throws  {
            return FieldNamingPolicy.separateCamelCase($r1.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };

    static String separateCamelCase(String $r0, String $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            char $c2 = $r0.charAt($i0);
            if (Character.isUpperCase($c2) && $r2.length() != 0) {
                $r2.append($r1);
            }
            $r2.append($c2);
        }
        return $r2.toString();
    }

    static String upperCaseFirstLetter(String $r1) throws  {
        StringBuilder $r0 = new StringBuilder();
        int $i0 = 0;
        char $c1 = $r1.charAt(0);
        while ($i0 < $r1.length() - 1 && !Character.isLetter($c1)) {
            $r0.append($c1);
            $i0++;
            $c1 = $r1.charAt($i0);
        }
        if ($i0 == $r1.length()) {
            return $r0.toString();
        }
        return !Character.isUpperCase($c1) ? $r0.append(modifyString(Character.toUpperCase($c1), $r1, $i0 + 1)).toString() : $r1;
    }

    private static String modifyString(char $c0, String $r0, int $i1) throws  {
        return $i1 < $r0.length() ? $c0 + $r0.substring($i1) : String.valueOf($c0);
    }
}
