package com.pokemon.pokemonrpg.service;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.pokemon.pokemonrpg.model.DamageCategory;
import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.model.Types;

public class AttackServiceTest {

    @Test
    void deveExecutarAtaqueFisico() throws Exception {

        Pokemon atacante = new Pokemon();
        atacante.setAttack(100); // Ataque físico alto
        atacante.setSpAttack(80); // Ataque especial médio
        atacante.setType1("FIRE");

        Pokemon defensor = new Pokemon();
        defensor.setDefense(90);
        defensor.setSpDefense(90);
        defensor.setType1("GRASS");

        AttackService service = new AttackService();

        Field field =
                AttackService.class.getDeclaredField(
                        "damageCalculate");

        field.setAccessible(true);
        field.set(service, new DamageCalculate());

        int dano = service.executeAttack(
                atacante,
                defensor,
                80,
                Types.FIRE,
                DamageCategory.PHYSICAL
        );

        assertTrue(dano > 0);
    }

    @Test
    void deveRetornarZeroParaStatus() throws Exception {

        Pokemon atacante = new Pokemon();
        Pokemon defensor = new Pokemon();

        AttackService service = new AttackService();

        Field field =
                AttackService.class.getDeclaredField(
                        "damageCalculate");

        field.setAccessible(true);
        field.set(service, new DamageCalculate());

        int dano = service.executeAttack(
                atacante,
                defensor,
                0,
                Types.NORMAL,
                DamageCategory.STATUS
        );

        assertEquals(0, dano);
    }

    @Test
    void danoCriticoDeveSerMaiorQueNormal() throws Exception {

        Pokemon atacante = new Pokemon();
        atacante.setAttack(120);
        atacante.setType1("FIRE");

        Pokemon defensor = new Pokemon();
        defensor.setDefense(80);
        defensor.setType1("GRASS");

        AttackService service = new AttackService();

        Field field =
                AttackService.class.getDeclaredField(
                        "damageCalculate");

        field.setAccessible(true);
        field.set(service, new DamageCalculate());

        int danoNormal = service.executeAttack(
                atacante,
                defensor,
                80,
                Types.FIRE,
                false,
                DamageCategory.PHYSICAL
        );

        int danoCritico = service.executeAttack(
                atacante,
                defensor,
                80,
                Types.FIRE,
                true,
                DamageCategory.PHYSICAL
        );

        assertTrue(danoCritico > danoNormal);
    }
}