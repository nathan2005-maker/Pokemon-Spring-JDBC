package com.pokemon.pokemonrpg;

import com.pokemon.pokemonrpg.model.Pokemon;
import com.pokemon.pokemonrpg.model.Types;
import com.pokemon.pokemonrpg.repository.PokemonRepository;
import com.pokemon.pokemonrpg.service.AttackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class PokemonRpgApplication implements CommandLineRunner {

    @Autowired
    private PokemonRepository repository;

    @Autowired
    private AttackService attackService;

    public static void main(String[] args) {
        SpringApplication.run(PokemonRpgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n==================================================");
        System.out.println("    SIMULAÇÃO DE RPG (BANCO SOMENTE LEITURA)      ");
        System.out.println("==================================================");

        // 1. CONSULTA NO JDBC (Apenas leitura dos status imutáveis da base)
        // Obs: Certifique-se de que os IDs 1 e 2 existem no seu SQLite!
        Optional<Pokemon> optAtacante = repository.findById(1); 
        Optional<Pokemon> optDefensor = repository.findById(2); 

        if (optAtacante.isPresent() && optDefensor.isPresent()) {
            Pokemon atacante = optAtacante.get();
            Pokemon defensor = optDefensor.get();

            System.out.println("\n🥊 COMBATENTES CARREGADOS DO SQLITE:");
            System.out.printf("⚡ Atacante: %s | Ataque Fixo: %d | Tipo: %s\n", 
                    atacante.getName(), atacante.getAttack(), atacante.getType1());
            System.out.printf("🛡️ Defensor: %s | Defesa Fixa: %d | HP Inicial: %d\n", 
                    defensor.getName(), defensor.getDefense(), defensor.getHp());

            // 2. PREPARANDO O GOLPE
            int movePower = 40;
            Types moveType = Types.WATER;

            System.out.printf("\n⚔️ %s usou um ataque do tipo %s (Poder: %d)!\n", 
                    atacante.getName(), moveType.name(), movePower);

            // 3. EXECUTANDO O CÁLCULO
            // Passa os 4 parâmetros exatos que o seu AttackService exige
            int danoCausado = attackService.executeAttack(atacante, defensor, movePower, moveType);
            System.out.println("💥 Dano Calculado: " + danoCausado);

            // 4. APLICANDO O DANO APENAS NA MEMÓRIA RAM
            int novoHp = Math.max(0, defensor.getHp() - danoCausado);
            defensor.setHp(novoHp); 

            System.out.println("\n🎮 RESULTADO PÓS-TURNO (Em Memória):");
            System.out.printf("🏥 HP restante de %s na sessão atual: %d\n", defensor.getName(), defensor.getHp());
            System.out.println("🚫 NADA FOI ALTERADO OU SALVO NO BANCO DE DADOS.");

        } else {
            System.out.println("\n⚠️ ERRO: Pokémons não encontrados. Verifique se os IDs informados existem no SQLite!");
        }

        System.out.println("==================================================\n");
    }
}