/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validar {

    // varifica si es valido la cedula ecuatoriana
    public static boolean esValidoCedulaEc(String cedula) {
        byte sum = 0;
        try {
            if (cedula.trim().length() != 10) {
                return false;
            }
            String[] data = cedula.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24) {
                return false;
            }
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++) {
                digits[i] = Byte.parseByte(data[i]);
            }
            if (digits[2] > 6) {
                return false;
            }
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9) {
                        verifier = (byte) (verifier - 9);
                    }
                } else {
                    verifier = (byte) (digits[i] * 1);
                }
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9]) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verifica el número de teléfono
    public static boolean esValidoTelefono(String s) {
        // El argumento dado para compile() método
        // es expresión regular. Con la ayuda de
        // expresión regular podemos validar el número de móvil.
        // El número debe ser de 10 dígitos.
        // Creación de un objeto de clase Patrón
        Pattern p = Pattern.compile("^\\d{10}$");

        // La clase de patrón contiene el método matcher()
        // para encontrar la coincidencia entre el número dado
        // y expresión regular para la cual
        // se crea el objeto de la clase Matcher
        Matcher m = p.matcher(s);

        // Retorna un valor boleano
        return (m.matches());
    }

    // Verificar si una cadena tiene solo letras
    public static boolean esSoloLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si no está entre a y z, ni entre A y Z, ni es un espacio
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return false;
            }
        }
        //Terminado el bucle sin que se haya retornado false, es que todos los caracteres son letras
        return true;
    }

}
