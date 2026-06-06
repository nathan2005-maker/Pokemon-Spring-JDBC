package com.pokemon.pokemonrpg.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CombatActionTest {

    @Test
    void attackServiceDeveImplementarCombatAction() {

        CombatAction action = new AttackService();

        assertNotNull(action);
        assertTrue(action instanceof CombatAction);
    }
}