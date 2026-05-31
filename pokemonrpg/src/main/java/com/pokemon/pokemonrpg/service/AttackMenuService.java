package com.pokemon.pokemonrpg.service;

import com.pokemon.pokemonrpg.model.Attack;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class AttackMenuService {

    public Attack selectAttack(Scanner scanner) {
        Attack[] todosAtaques = Attack.values();
        
        for (int i = 0; i < todosAtaques.length; i++) {
            System.out.println((i + 1) + ". " + todosAtaques[i].getName() + 
                               " | Tipo: " + todosAtaques[i].getType() + 
                               " | Cat: " + todosAtaques[i].getCategory());
        }

        int escolha = -1;
        while (escolha < 1 || escolha > todosAtaques.length) {
            System.out.print("Digite o número do ataque (1-" + todosAtaques.length + "): ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
            } else {
                scanner.next(); 
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
        
        return todosAtaques[escolha - 1];
    }
}