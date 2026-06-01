package com.pokemon.pokemonrpg.service;

public class  ExceptionError extends RuntimeException {
	//Construtor
    public ExceptionError(String message){
        super(message);
    }
}