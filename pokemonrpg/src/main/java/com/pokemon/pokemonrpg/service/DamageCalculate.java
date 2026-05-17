package com.pokemon.pokemonrpg.service;

import org.springframework.stereotype.Component;

@Component
public class DamageCalculate {

    /**
     * Aplica a fórmula matemática oficial do jogo truncando as divisões inteiras.
     */
    public int calculateRawDamage(int level, int basePower, int attackStat, int defenseStat, double modifiers) {
        // Passo 1: ((2 * Level) / 5) + 2
        int step1 = ((2 * level) / 5) + 2;

        // Passo 2: Multiplica pelo BasePower e Attack, depois divide pela Defense
        int step2 = (step1 * basePower * attackStat) / defenseStat;

        // Passo 3: Divide por 50 e soma 2
        int baseDamage = (step2 / 50) + 2;

        // Passo 4: Aplica os modificadores finais e trunca para inteiro
        return (int) Math.floor(baseDamage * modifiers);
    }
}