package org.example;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CalculadoraDeNotas calculadora = new CalculadoraDeNotas();
        List<Double> notas = List.of(7.0, 8.0, 9.0);
        double media = calculadora.calcularMedia(notas);
        System.out.println("Media das notas: " + media);
    }
}
