package com.geekbrains.backend.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты функциональности класса Hello")
class HelloTest extends FunctionalTest{

    static Hello hello;

    @BeforeAll
    static void beforeAll() {
        hello = new Hello();
    }

    public static Stream<Arguments> paramsForSum() {
        return Stream.of(
                Arguments.of(2,3,5),
                Arguments.of(5,6,11),
                Arguments.of(1,-1,0)
        );
    }

    // value check
    @Test
    void testFoo() {
        assertEquals(5, hello.sum(2,3));
    }
    
    @ParameterizedTest(name = "Тест#{index}: {0} + {1} = {2}")
    @MethodSource("paramsForSum")
    @DisplayName("Тест суммы двух чисел")
    void testFooWithParams(int a, int b, int expected) {
        assertEquals(expected, hello.sum(a, b));
    }

    // exception check
    @Test
    @DisplayName("Тест классическое деление без ошибок")
    void testDivideCommonCase() {

        assertDoesNotThrow(new Executable() {

            @Override
            public void execute() throws Throwable {
                hello.divide(6,3);
            }
        });
    }

    @Test
    @DisplayName("Тест на ошибку при делении на 0")
    void testDivideByZero() {
        assertThrows(
                ArithmeticException.class,
                () -> hello.divide(1, 0)
        );
    }

    @Test
    @DisplayName("Тест зачитывания ресурса")
    void testWithResource() throws IOException {
        String resource = getStringResource("data.json");
        System.out.println(resource);
        assertTrue(resource.contains("result"));
    }



}