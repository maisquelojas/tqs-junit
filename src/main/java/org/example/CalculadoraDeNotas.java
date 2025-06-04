package org.example;

import java.util.List;

public class CalculadoraDeNotas {

    public static final double NOTA_MINIMA_VALIDA = 0.0;
    public static final double NOTA_MAXIMA_VALIDA = 10.0;

    /**
     * Calcula a média de uma lista de notas.
     * Lança IllegalArgumentException se alguma nota for inválida ou a lista estiver
     * vazia.
     */
    public double calcularMedia(List<Double> notas) {
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("A lista de notas não pode ser nula ou vazia.");
        }

        double soma = 0;
        for (Double nota : notas) {
            if (nota == null || nota < NOTA_MINIMA_VALIDA || nota > NOTA_MAXIMA_VALIDA) {
                throw new IllegalArgumentException("Nota inválida: " + nota + ". As notas devem estar entre "
                        + NOTA_MINIMA_VALIDA + " e " + NOTA_MAXIMA_VALIDA + ".");
            }
            soma += nota;
        }
        return soma / notas.size();
    }

    /**
     * Verifica se um aluno foi aprovado.
     * A média deve ser maior ou igual à nota de corte.
     */
    public boolean verificarAprovacao(double mediaCalculada, double notaDeCorte) {
        if (notaDeCorte < NOTA_MINIMA_VALIDA || notaDeCorte > NOTA_MAXIMA_VALIDA) {
            throw new IllegalArgumentException("Nota de corte inválida. Deve estar entre " + NOTA_MINIMA_VALIDA + " e "
                    + NOTA_MAXIMA_VALIDA + ".");
        }
        // Consideramos que a média já foi validada pelo método calcularMedia
        return mediaCalculada >= notaDeCorte;
    }
}