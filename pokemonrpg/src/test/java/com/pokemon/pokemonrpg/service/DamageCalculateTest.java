package com.pokemon.pokemonrpg.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DamageCalculateTest {

    private final DamageCalculate damageCalculate = new DamageCalculate();

    @Test
    void deveCalcularDanoCorretamente() {
        int dano = damageCalculate.calculateRawDamage(
                50,
                80,
                100,
                90,
                1.0
        );

        assertTrue(dano > 0);
    }

    @Test
    void deveRetornarDanoMaiorComModificadorMaior() {

        int danoNormal = damageCalculate.calculateRawDamage(
                50, 80, 100, 90, 1.0
        );

        int danoAumentado = damageCalculate.calculateRawDamage(
                50, 80, 100, 90, 2.0
        );

        assertTrue(danoAumentado > danoNormal);
    }

    @Test
    void deveRetornarValorEsperado() {

        int dano = damageCalculate.calculateRawDamage(
                50, 80, 100, 100, 1.0
        );

        assertEquals(37, dano);
    }
}