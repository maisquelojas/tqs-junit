package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculadoraDeNotasTest {

    private CalculadoraDeNotas calculadora;

    @BeforeEach // Executa antes de cada método de teste
    void setUp() {
        calculadora = new CalculadoraDeNotas();
        System.out.println("Instanciando CalculadoraDeNotas...");
    }

    @Test
    @DisplayName("Deve calcular a média corretamente para notas válidas")
    void deveCalcularMediaCorretamente() {
        // Arrange (Organizar/Preparar)
        List<Double> notas = Arrays.asList(7.0, 8.0, 9.0);
        double mediaEsperada = 8.0;

        // Act (Agir/Executar)
        double mediaCalculada = calculadora.calcularMedia(notas);

        // Assert (Verificar)
        assertEquals(mediaEsperada, mediaCalculada, 0.001, "A média calculada está incorreta.");
    }

    @Test
    @DisplayName("Deve calcular média para uma única nota válida")
    void deveCalcularMediaParaUmaNota() {
        List<Double> notas = Collections.singletonList(10.0);
        assertEquals(10.0, calculadora.calcularMedia(notas), 0.001);
    }

    @Test
    @DisplayName("Deve lançar exceção para lista de notas nula ao calcular média")
    void deveLancarExcecaoParaListaNula() {
        // Arrange
        List<Double> notas = null;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularMedia(notas), "Deveria lançar IllegalArgumentException para lista nula.");
        assertEquals("A lista de notas não pode ser nula ou vazia.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para lista de notas vazia ao calcular média")
    void deveLancarExcecaoParaListaVazia() {
        List<Double> notas = Collections.emptyList();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularMedia(notas));
        assertEquals("A lista de notas não pode ser nula ou vazia.", exception.getMessage());
    }

    @ParameterizedTest // Teste parametrizado
    @ValueSource(doubles = { -1.0, 11.0, -0.01, 10.01 }) // Valores de entrada para o parâmetro
    @DisplayName("Deve lançar exceção para nota fora do intervalo válido")
    void deveLancarExcecaoParaNotaForaDoIntervalo(double notaInvalida) {
        List<Double> notas = Arrays.asList(7.0, notaInvalida, 9.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularMedia(notas));
        assertTrue(exception.getMessage().contains("Nota inválida: " + notaInvalida),
                "Mensagem de exceção não contém a nota inválida.");
    }

    @Test
    @DisplayName("Deve lançar exceção para nota nula na lista")
    void deveLancarExcecaoParaNotaNulaNaLista() {
        List<Double> notas = Arrays.asList(7.0, null, 9.0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularMedia(notas));
        assertTrue(exception.getMessage().contains("Nota inválida: null"),
                "Mensagem de exceção não contém 'Nota inválida: null'.");
    }

    // Testes para verificarAprovacao

    @Test
    @DisplayName("Deve retornar true para aluno aprovado")
    void deveRetornarTrueParaAlunoAprovado() {
        assertTrue(calculadora.verificarAprovacao(7.0, 7.0), "Média igual à nota de corte deve aprovar.");
        assertTrue(calculadora.verificarAprovacao(7.1, 7.0), "Média maior que a nota de corte deve aprovar.");
    }

    @Test
    @DisplayName("Deve retornar false para aluno reprovado")
    void deveRetornarFalseParaAlunoReprovado() {
        assertFalse(calculadora.verificarAprovacao(6.9, 7.0), "Média menor que a nota de corte deve reprovar.");
    }

    @ParameterizedTest
    @MethodSource("cenariosDeAprovacao") // Usa um método para fornecer os argumentos
    @DisplayName("Deve verificar aprovação para diversos cenários")
    void deveVerificarAprovacaoParaDiversosCenarios(double media, double corte, boolean esperado) {
        assertEquals(esperado, calculadora.verificarAprovacao(media, corte));
    }

    // Método que fornece os argumentos para o teste parametrizado acima
    static Stream<Arguments> cenariosDeAprovacao() {
        return Stream.of(
                Arguments.of(7.0, 7.0, true), // media, corte, esperado
                Arguments.of(10.0, 7.0, true),
                Arguments.of(6.99, 7.0, false),
                Arguments.of(0.0, 5.0, false),
                Arguments.of(5.0, 5.0, true));
    }

    @ParameterizedTest
    @ValueSource(doubles = { -0.1, 10.1 })
    @DisplayName("Deve lançar exceção para nota de corte inválida")
    void deveLancarExcecaoParaNotaDeCorteInvalida(double notaCorteInvalida) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculadora.verificarAprovacao(7.0, notaCorteInvalida));
        assertTrue(exception.getMessage().contains("Nota de corte inválida"));
    }
}
