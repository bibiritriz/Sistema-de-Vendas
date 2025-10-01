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

    private boolean isValido(String texto) {
        switch (tipo) {
            case LETRAS:
                return texto.matches("[a-zA-ZÀ-ÿ\\s]+"); // letras, acentos e espaço
            case NUMEROS:
                return texto.matches("\\d+"); // apenas dígitos
            case ALFANUMERICO:
                return texto.matches("[a-zA-ZÀ-ÿ0-9\\s]+"); // letras + números
            default:
                return false;
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string != null 
                && isValido(string) 
                && fb.getDocument().getLength() + string.length() <= limite) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs)
            throws BadLocationException {
        if (string != null 
                && isValido(string) 
                && fb.getDocument().getLength() - length + string.length() <= limite) {
            super.replace(fb, offset, length, string, attrs);
        }
    }
}
