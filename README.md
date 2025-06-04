**Duração Estimada Total: 10-15 minutos**

---

**Roteiro da Apresentação (Foco Prático)**

**(Início Imediato - Sem Slide de Capa)**

**Slide 1: Testes Unitários e JUnit: Uma Visão Rápida (Teoria - ~3 minutos)**

*   **Título do Slide:** Testes Unitários e JUnit: Por Que e Como?
*   **O que é Teste Unitário?** (30 segundos)
    *   Testar a menor parte funcional do seu código (método/classe) de forma isolada.
    *   Rápido, automatizado, repetível.
*   **Por que Testar? Benefícios Chave** (1 minuto)
    *   **Confiança:** Garante que seu código faz o esperado.
    *   **Detecção Precoce de Bugs:** Encontra problemas antes que cheguem longe.
    *   **Refatoração Segura:** Mude seu código sem medo de quebrar o que já funciona.
    *   **Documentação Viva:** Testes mostram como usar seu código.
*   **O que é JUnit?** (1 minuto)
    *   Framework padrão para testes unitários em Java.
    *   JUnit 5 (Jupiter): Moderno, modular, com muitos recursos.
    *   Facilita a escrita e execução de testes automatizados.
*   **Como Adicionar ao Projeto (Exemplo Gradle)?** (30 segundos)
    *   Mostrar *apenas* o snippet essencial do `build.gradle.kts`:
        ```kotlin
        // build.gradle.kts
        dependencies {
            testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
            testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
            testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
        }
        ```
    *   "Adicionamos essas linhas ao nosso arquivo de build, e o Gradle cuida do resto."

---

**(Transição para a IDE)**

**Parte Prática: Testando uma `CalculadoraDeNotas` (Código e Testes - ~10 minutos)**

*   **Objetivo:** Mostrar o JUnit em ação, como escrever e rodar testes, e os tipos de testes que estamos fazendo.
*   **Ambiente:** IDE com `CalculadoraDeNotas.java` e `CalculadoraDeNotasTest.java` abertos.

1.  **Apresentar a Classe `CalculadoraDeNotas.java` (30 segundos)**
    *   Mostrar rapidamente o código da `CalculadoraDeNotas`.
    *   "Esta é a classe que vamos testar. Ela calcula médias e verifica aprovação."

2.  **Estrutura Básica de um Teste JUnit (`CalculadoraDeNotasTest.java`) (2 minutos)**
    *   Mostrar a classe de teste.
    *   **`@BeforeEach` e `setUp()`:** "Antes de cada teste, criamos uma nova instância da calculadora para garantir isolamento."
        ```java
        private CalculadoraDeNotas calculadora;

        @BeforeEach
        void setUp() {
            calculadora = new CalculadoraDeNotas();
        }
        ```
    *   **Primeiro Teste: Caminho Feliz (`deveCalcularMediaCorretamente`)**
        *   "Nosso primeiro tipo de teste é o 'caminho feliz', verificando o comportamento esperado com entradas válidas."
        *   Mostrar o teste:
            ```java
            @Test
            @DisplayName("Deve calcular a média corretamente para notas válidas")
            void deveCalcularMediaCorretamente() {
                // Arrange
                List<Double> notas = Arrays.asList(7.0, 8.0, 9.0);
                double mediaEsperada = 8.0;
                // Act
                double mediaCalculada = calculadora.calcularMedia(notas);
                // Assert
                assertEquals(mediaEsperada, mediaCalculada, 0.001);
            }
            ```
        *   Explicar brevemente `@Test`, `@DisplayName`, e o padrão AAA (Arrange, Act, Assert) enquanto mostra o código.
        *   **Rodar este teste e mostrar o resultado VERDE.** "Passou!"

3.  **Testando Tratamento de Erros e Validações (3 minutos)**
    *   "Agora, vamos testar como nosso código lida com entradas inválidas. Este é um tipo de 'teste de exceção' ou 'teste de validação de entrada'."
    *   **Usando `assertThrows` para Lista Nula/Vazia:**
        *   Mostrar o teste `deveLancarExcecaoParaListaNula`:
            ```java
            @Test
            @DisplayName("Deve lançar exceção para lista de notas nula")
            void deveLancarExcecaoParaListaNula() {
                IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculadora.calcularMedia(null)
                );
                assertEquals("A lista de notas não pode ser nula ou vazia.", exception.getMessage());
            }
            ```
        *   Explicar `assertThrows` e como ele verifica o tipo e a mensagem da exceção.
        *   **Rodar e mostrar VERDE.**
    *   **Testando Notas Inválidas (Individual):**
        *   Mostrar um teste simples para uma nota inválida (ex: -1.0) usando `assertThrows`.

4.  **Otimizando com Testes Parametrizados (3 minutos)**
    *   "Para testar várias entradas inválidas sem repetir código, usamos 'testes parametrizados'."
    *   **`@ParameterizedTest` e `@ValueSource`:**
        *   Mostrar o teste `deveLancarExcecaoParaNotaForaDoIntervalo`:
            ```java
            @ParameterizedTest
            @ValueSource(doubles = {-1.0, 11.0, -0.01, 10.01})
            @DisplayName("Deve lançar exceção para nota fora do intervalo válido")
            void deveLancarExcecaoParaNotaForaDoIntervalo(double notaInvalida) {
                List<Double> notas = Arrays.asList(7.0, notaInvalida, 9.0);
                assertThrows(IllegalArgumentException.class, () -> calculadora.calcularMedia(notas));
            }
            ```
        *   Explicar como `@ValueSource` fornece os diferentes valores para `notaInvalida`.
        *   **Rodar e mostrar VERDE para todas as invocações.**

5.  **A Mágica: Vendo um Teste Falhar (1.5 minutos)**
    *   **Introduzir um Bug:** Voltar para `CalculadoraDeNotas.java`. Mudar `return soma / notas.size();` para `return soma / notas.size() + 0.1;`.
    *   **Rodar os Testes Novamente:**
        *   Mostrar o resultado VERMELHO.
        *   Apontar qual teste falhou (ex: `deveCalcularMediaCorretamente`).
        *   Mostrar a mensagem de erro do JUnit: "expected: <8.0> but was: <8.1>".
        *   "É aqui que o JUnit brilha! Ele nos avisa imediatamente quando uma mudança quebra algo que funcionava."
    *   **Corrigir o Bug:** Desfazer a alteração.
    *   **Rodar os Testes Novamente:** Mostrar o resultado VERDE. "Tudo certo de novo!"

---

**Slide 2: Conclusão Rápida e Próximos Passos (Teoria/Prática - ~2 minutos)**

*   **Título do Slide:** JUnit na Prática: Benefícios e Além
*   **Recapitulando os Benefícios Vistos na Prática:** (30 segundos)
    *   Confiança ao alterar código (vimos com o bug).
    *   Feedback rápido sobre o estado do código.
    *   Testes como documentação do comportamento esperado.
*   **Tipos de Testes que Fizemos (Resumo):** (30 segundos)
    *   **Caminho Feliz:** Funcionalidade principal.
    *   **Validação de Entradas / Testes de Exceção:** Como o sistema lida com dados ruins.
    *   **Testes Parametrizados:** Eficiência para múltiplas entradas similares.
*   **Onde Ir a Partir Daqui? (Breve Menção)** (1 minuto)
    *   **Mockito:** Para "mockar" dependências e testar classes de forma verdadeiramente isolada (ex: se a calculadora dependesse de um banco de dados).
    *   **TDD (Test-Driven Development):** Escrever os testes *antes* do código de produção.
    *   **Cobertura de Teste (JaCoCo):** Ferramentas para ver quanto do seu código está coberto por testes.
*   **Mensagem Final:** (30 segundos)
    *   "JUnit é uma ferramenta poderosa e relativamente simples de começar a usar."
    *   "Incorporar testes unitários melhora drasticamente a qualidade e a manutenibilidade do seu código. Comecem a testar!"
