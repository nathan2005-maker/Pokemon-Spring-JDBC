package com.pokemon.pokemonrpg.service;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.model.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttackService {

    @Autowired
    private DamageCalculate damageCalculate;

    /**
     * Orquestra o ataque e calcula os modificadores sem alterar as stats base.
     */
    public int executeAttack(Pokemon atacante, Pokemon defensor, int movePower, Types moveType) {
        // 1. Calcula STAB, Eficácia de Tipo e o fator aleatório (RNG)
        double stab = calcularStab(atacante, moveType);
        double eficacia = calcularEficacia(defensor, moveType);
        double rng = 0.85 + (Math.random() * 0.15);

        double totalModifiers = stab * eficacia * rng;
        int level = 50; // Nível fixo padronizado

        // 2. Delega o cálculo pura e simplesmente lendo os status imutáveis
        return damageCalculate.calculateRawDamage(
                level,
                movePower,
                atacante.getAttack(),
                defensor.getDefense(),
                totalModifiers
        );
    }

    // ========================================================================
    // MÉTODOS PRIVADOS DE VALIDAÇÃO
    // ========================================================================

    private double calcularStab(Pokemon atacante, Types moveType) {
        if (atacante.getType1() != null && moveType.name().equalsIgnoreCase(atacante.getType1().trim())) {
            return 1.5;
        }
        if (atacante.getType2() != null && moveType.name().equalsIgnoreCase(atacante.getType2().trim())) {
            return 1.5;
        }
        return 1.0;
    }

    private double calcularEficacia(Pokemon defensor, Types moveType) {
        double multiplicador = 1.0;

        // Valida Tipo 1
        if (defensor.getType1() != null && !defensor.getType1().trim().isEmpty()) {
            try {
                Types defensorTipo1 = Types.valueOf(defensor.getType1().trim().toUpperCase());
                multiplicador *= moveType.calcularEfetividadeContra(defensorTipo1);
            } catch (IllegalArgumentException e) {
                // Proteção caso o texto no banco esteja com caracteres inválidos
            }
        }

        // Valida Tipo 2 (Ignorando nulos e a string literal "NULL")
        if (defensor.getType2() != null && 
            !defensor.getType2().trim().equalsIgnoreCase("NULL") && 
            !defensor.getType2().trim().isEmpty()) {
            try {
                Types defensorTipo2 = Types.valueOf(defensor.getType2().trim().toUpperCase());
                multiplicador *= moveType.calcularEfetividadeContra(defensorTipo2);
            } catch (IllegalArgumentException e) {
                // Proteção caso o texto no banco esteja com caracteres inválidos
            }
        }

        return multiplicador;
    }
}