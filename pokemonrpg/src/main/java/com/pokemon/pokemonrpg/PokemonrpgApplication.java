package com.pokemon.pokemonrpg;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.model.Types;
import com.pokemon.pokemonrpg.repository.PokemonRepository;
import com.pokemon.pokemonrpg.service.CombatAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class PokemonrpgApplication implements CommandLineRunner {

    @Autowired
    private PokemonRepository repository;

    @Autowired
    private CombatAction attackService;

    public static void main(String[] args) {
        SpringApplication.run(PokemonrpgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        

        List<Pokemon> pokemons = repository.findAll();
        for (Pokemon p : pokemons) {
            if (p.getType2() != null && !p.getType2().trim().equalsIgnoreCase("NULL") && !p.getType2().trim().isEmpty()) {
                System.out.printf("ID: %d | Nome: %s | Tipos: %s & %s\n", p.getId(), p.getName(), p.getType1().trim(), p.getType2().trim());
            } else {
                System.out.printf("ID: %d | Nome: %s | Tipo: %s\n", p.getId(), p.getName(), p.getType1().trim());
            }
        }
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);

        String escolhaPlayerUm = ("""
        		PLAYER 1:
        		ESCOLHA O SEU POKEMON (1-152):
        		""");
        System.out.print(escolhaPlayerUm);		
        int idAtacante = scanner.nextInt();

        String escolhaPlayerDois = ("""
        		PLAYER 2:
        		ESCOLHA O SEU POKEMON (1-152):
        		""");
        System.out.print(escolhaPlayerDois);
        int idDefensor = scanner.nextInt();

        // 1. CONSULTA NO JDBC (Apenas leitura dos status imutáveis da base)
        // Obs: Certifique-se de que os IDs 1 e 2 existem no seu SQLite!
        Optional<Pokemon> optAtacante = repository.findById(idAtacante); 
        Optional<Pokemon> optDefensor = repository.findById(idDefensor); 

        
        }

}