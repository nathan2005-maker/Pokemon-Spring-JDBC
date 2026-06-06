package com.pokemon.pokemonrpg.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.service.AttackMenuService; // IMPORT ADICIONADO
import com.pokemon.pokemonrpg.service.CombatAction;

@JdbcTest(excludeAutoConfiguration = {
    FlywayAutoConfiguration.class,
    LiquibaseAutoConfiguration.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(PokemonRepository.class) 
@TestPropertySource(locations = "classpath:application-test.properties") 
class PokemonRepositoryTest {

	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@MockBean
	private CombatAction combatAction; 
	
	@MockBean
	private AttackMenuService attackMenuService;
	
	@BeforeEach
	void setUp() {
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
	@DisplayName("Should get ID sucessfully from JDBC")
	void searchForPokémonByIdSucess() {
		// Arrange
		jdbcTemplate.update(
			"INSERT INTO pokemon (id, name, hp, attack, defense, Type1, Type2, speed, spAttack, spDefense) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
			1, "Bulbasaur", 45, 49, 49, "Grass", "Poison", 45, 65, 65
		);
		
		// Act
		Optional<Pokemon> resultPokemon = pokemonRepository.findById(1);
		
		// Assert
		assertTrue(resultPokemon.isPresent(), "Pokémon ID 1 found.");
		assertEquals("Bulbasaur", resultPokemon.get().getName());
		assertEquals("Grass", resultPokemon.get().getType1());
		assertEquals(49, resultPokemon.get().getAttack());
		
		System.out.println("Sucesso! Pokémon encontrado: " + resultPokemon.get().getName());
	}
}