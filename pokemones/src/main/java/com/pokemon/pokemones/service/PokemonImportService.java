package com.pokemon.pokemones.service;

import java.io.BufferedReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.services.ImportDialogService;

@Service
public class PokemonImportService extends ImportDialogService{

	private final PokemonCsvImportService importservice;
	
	public @Autowired PokemonImportService(final PokemonCsvImportService importservice) {
		super();
		this.importservice = importservice;
	}
	
	public void show() {
		super.showImportDialog(r->{
				if(r==null) {
					return;
				}
				
				try (BufferedReader reader = new BufferedReader(r)){
					
					importservice.performImport(reader);
					
				}catch (Exception e) {
					LOG.error("error", e);
				}
		});
		
	}
	
}
