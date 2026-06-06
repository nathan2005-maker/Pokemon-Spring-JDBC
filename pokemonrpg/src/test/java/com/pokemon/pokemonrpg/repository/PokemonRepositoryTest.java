package com.pokemon.pokemonrpg.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.pokemon.pokemonrpg.model.Pokemon;
@JdbcTest 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(PokemonRepository.class)
class PokemonRepositoryTest {

	
	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@BeforeEach
	void setUp() {
		//Create table
		jdbcTemplate.execute("DROP TABLE IF EXISTS pokemon");
		jdbcTemplate.execute("CREATE TABLE pokemon ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, "
				+ "hp INTEGER, "
				+ "attack INTEGER, "
				+ "defense INTEGER, "
				+ "Type1 TEXT, "
				+ "Type2 TEXT, "
				+ "speed INTEGER, "
				+ "spAttack INTEGER, "
				+ "spDefense INTEGER"
				+ ")");
	}
	
	@Test
	void searchForPokémonByID() {
		
	}
	

}