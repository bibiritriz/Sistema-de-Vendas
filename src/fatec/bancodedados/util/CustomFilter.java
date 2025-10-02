package fatec.bancodedados.util;

import javax.swing.text.*;

public class CustomFilter extends DocumentFilter {
    private int limite;
    private Tipo tipo;

    public enum Tipo {
        LETRAS, NUMEROS, ALFANUMERICO
    }

    public CustomFilter(int limite, Tipo tipo) {
        this.limite = limite;
        this.tipo = tipo;
    }
    public static boolean isEmailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(regex);
    }
        
    public static boolean isCPFValido(String cpf) {
        if (cpf == null) return false;

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma1 = 0;
            for (int i = 0; i < 9; i++) {
                soma1 += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int digito1 = 11 - (soma1 % 11);
            if (digito1 >= 10) digito1 = 0;

            int soma2 = 0;
            for (int i = 0; i < 10; i++) {
                soma2 += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int digito2 = 11 - (soma2 % 11);
            if (digito2 >= 10) digito2 = 0;

            return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                   digito2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValido(String texto) {
        if (texto.isEmpty()) return true;
        switch (tipo) {
            case LETRAS:
                return texto.matches("[a-zA-ZÀ-ÿ\\s]+");
            case NUMEROS:
                return texto.matches("\\d+");
            case ALFANUMERICO:
                return texto.matches("[a-zA-ZÀ-ÿ0-9\\s]+");
            default:
                return false;
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string != null 
                && (isValido(string) 
                && fb.getDocument().getLength() + string.length() <= limite)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
            throws BadLocationException {
        if (string != null 
                && (isValido(string) 
                && fb.getDocument().getLength() - length + string.length() <= limite)) {
            super.replace(fb, offset, length, string, attrs);
        }
    }
}
