package com.pokemon.pokemonrpg;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.SymbolPokemon;
import com.pokemon.pokemonrpg.model.Attack;
import com.pokemon.pokemonrpg.repository.PokemonRepository;
import com.pokemon.pokemonrpg.service.CombatAction;
import com.pokemon.pokemonrpg.service.ExceptionError;
import com.pokemon.pokemonrpg.service.AttackMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class PokemonrpgApplication implements CommandLineRunner {

    @Autowired
    private PokemonRepository repository;

    @Autowired
    private CombatAction attackService;
    
    @Autowired
    private AttackMenuService attackMenuService;

    public static void main(String[] args) {
        SpringApplication.run(PokemonrpgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	
    	SymbolPokemon.exibirMenu();
    	Thread.sleep(5000);
        
    	try {
            List<Pokemon> pokemons = repository.findAll();
            for (Pokemon p : pokemons) {
                if (p.getType2() != null && !p.getType2().trim().equalsIgnoreCase("NULL") && !p.getType2().trim().isEmpty()) {
                    System.out.printf("ID: %d | Nome: %s | Tipos: %s & %s\n", p.getId(), p.getName(), p.getType1().trim(), p.getType2().trim());
                } else {
                    System.out.printf("ID: %d | Nome: %s | Tipo: %s\n", p.getId(), p.getName(), p.getType1().trim());
                }
            }
        
        // Bank Offline or inaccessible 
        } catch (org.springframework.transaction.CannotCreateTransactionException ex) {
            System.out.println("Error: Unable to connect to DataBase!");
            return; // Finishing
            
        // Unexpected connection error
        } catch (Exception ex) {
            System.out.println("Error: An unexpected error occurred while loading data.");
            return;  // Finishing
        }
        
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);
        int idAtacante = 0;
        int idDefensor = 0;
        
        try {
            try {
                String escolhaPlayerUm = ("""
                		SELECT: POKEMON 1:
                		ESCOLHA O SEU POKEMON (1-151):
                		""");
                System.out.print(escolhaPlayerUm);		
                idAtacante = scanner.nextInt();

                String escolhaPlayerDois = ("""
                		SELECT: POKEMON 2:
                		ESCOLHA O SEU POKEMON (1-151):
                		""");
                System.out.print(escolhaPlayerDois);
                idDefensor = scanner.nextInt();
                
            } catch (java.util.InputMismatchException ex) {
                //Input String
                throw new ExceptionError("Error: Invalid input! You must type a number, not a string.");
            }
            
            Optional<Pokemon> optAtacante = repository.findById(idAtacante); 
            Optional<Pokemon> optDefensor = repository.findById(idDefensor); 
            
            //Input verification
            if (!optAtacante.isPresent() || !optDefensor.isPresent()) {
                throw new ExceptionError("Error: Pokemon NOT found");
            }
            
            System.out.println("==================================================");
            
            Pokemon atacante = optAtacante.get();
            Pokemon defensor = optDefensor.get();

            System.out.println("==================================================");
            
            Attack ataqueEscolhido = attackMenuService.selectAttack(scanner);
            
            int danoAtacante = attackService.executeAttack(
                atacante, 
                defensor, 
                ataqueEscolhido.getPower(), 
                ataqueEscolhido.getType(), 
                ataqueEscolhido.getCategory() 
            );
            
            System.out.println("==================================================");
            System.out.println(atacante.getName() + " usou " + ataqueEscolhido.getName() + "!");
            
            int attVidaPokemonDefensor = defensor.getHp() - danoAtacante;
            if(attVidaPokemonDefensor <= 0) {
            	System.out.println(" WINNER CHAMPIONS!");
            	System.out.println("Vida de " + defensor.getName());
            }
            
            
            System.out.println("Dano causado: " + danoAtacante);
            System.out.println("Vida Defensor: " + "");
            
            System.out.println("Pokemon " + defensor.getName() + " se preparando...");
            String escolhaAttackPokemonDois = ("""
            		CONTRA-ATAQUE DEFENSOR:
            		SELECT ATTACK:
            		""");
            System.out.print(escolhaAttackPokemonDois);
            int indiceAtaqueDefensor = scanner.nextInt() - 1;
            
            /*
            Attack ataqueEscolhido = attackMenuService.selectAttack(scanner);
            int danoDefensor = attackService.executeAttack(
                    defensor, 
                    atacante, 
                    ataqueEscolhido.getPower(), 
                    ataqueEscolhido.getType(), 
                    ataqueEscolhido.getCategory() 
                );*/
                
        } catch (ExceptionError ex) {
            System.out.println(ex.getMessage()); 
        }finally {
                System.out.println("Closing processes");
            }
            
        }
    }