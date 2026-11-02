package io.github.Maike2961.validation_cpf.adapters.out.repository;

import java.util.Set;

public class ValidateCPFRepository {
    public static boolean isInvalidCPF(String cpf) {
        return INVALID_CPF.contains(cpf) || !cpf.matches("\\d{11}");
    }
    private static final Set<String> INVALID_CPF = Set.of(
            "00000000000",
            "11111111111",
            "22222222222",
            "33333333333",
            "44444444444",
            "55555555555",
            "66666666666",
            "77777777777",
            "88888888888",
            "99999999999"
    );
    public static boolean isValidCPF(String cpf) {
        if (cpf.isEmpty()){
            return false;
        }
        String digits = cpf.replaceAll("\\D", "");

        if (isInvalidCPF(digits)) {
            return false;
        }

        int firstDigit = calculateDigit(digits, 9);
        int secondDigit = calculateDigit(digits, 10);
        return firstDigit == Character.getNumericValue(digits.charAt(9)) &&
                secondDigit == Character.getNumericValue(digits.charAt(10));
    }

    private static int calculateDigit(String cpf, int length) {
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (length + 1 - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
    public static String formatCPF(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
