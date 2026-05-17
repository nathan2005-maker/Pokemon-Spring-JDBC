package com.pokemon.pokemonrpg.service;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.model.Types;

public interface CombatAction {
    int executeAttack(Pokemon atacante, Pokemon defensor, int movePower, Types moveType);
    int executeAttack(Pokemon atacante, Pokemon defensor, int movePower, Types moveType, boolean isCritical);
}