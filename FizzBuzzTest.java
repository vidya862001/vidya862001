package com.code.testcode.Math;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzTest {

@Test
    public void testFizzBuzz1 () {

        List<String> result = FizzBuzz.fizzBuzz (3);
        assertEquals (List.of("1", "2", "Fizz"), result);

    }

    @Test
    public void testFizzBuzz2 () {
        List<String> result = FizzBuzz.fizzBuzz (15);
        assertEquals(List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"), result);
    }
}