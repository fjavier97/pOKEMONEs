package com.pokemon.pokemones.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.event.ComponentDataRefreshEvent;
import com.pokemon.pokemones.core.event.ComponentViewRefreshEvent;
import com.pokemon.pokemones.core.job.AbstractJobPerformable;
import com.pokemon.pokemones.core.job.JobPerformable;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.repository.PokemonDAO;

@JobPerformable
public class PokemonCsvImportJob extends AbstractJobPerformable<Void>{

	private final Logger LOG;	

	private final static int NOMBRE = 0;
	private final static int POKEDEX_NO = 1;
	private final static int FORMA = 2;
	private final static int TIPO1 = 3;
	private final static int TIPO2 = 4;
	private final static int ATK = 5;
	private final static int DEF = 6;
	private final static int SPA = 7;
	private final static int SPD = 8;
	private final static int SPE = 9;
	private final static int HP = 10;

	private final static int _HEADERS = 11;

	private final PokemonDAO repository;
	
	private Reader reader;	

	public @Autowired PokemonCsvImportJob(final PokemonDAO repository){
		super();
		this.repository = repository;
		LOG = LoggerFactory.getLogger(PokemonCsvImportJob.class);
	}

	@Override
	public void setParams(Map<String, Object> params) {
		this.reader = (Reader)params.get("reader");
	}
	
	public Void perform() throws Exception{

		try (final BufferedReader reader = new BufferedReader(this.reader)){				
			
			final String[] headers = reader.readLine().split(";");
			final Integer[] hi = getHeaderIndexes(headers);
			
			//primero leo todo el archivo para poder llevar el estado
			final List<String> lineas = new LinkedList<String>();
			for(String linea = null;(linea=reader.readLine())!=null;) {
				lineas.add(linea);
			}
	
			/* itero sobre los datos */
			/* por cada campo comprobar:
			 * 	-que esta declarada la columna en la cabecera
			 * 	-que el campo no este vacio
			 * 	-si es numerico o enumerado que se pueda parsear coreectamente 
			 * 	+si no esta declarado o esta vacio, se ignorael campo pero se procesa la linea, si hay error no se procesarála linea*/	
			for(int k = 0;k<lineas.size();k++) {
	
				final String line = lineas.get(k);

				if(line.isEmpty()) {/* esta comprobacion no tiene mucho sentido, porque las lineas vacias son [;;;;;;;;....], pero por si acaso*/
					continue;
				}
				
				final String[] data = line.split(";");
				
				if(data.length==0) {/* si la linea no tiene datos (es decir[;;;;;;;;....]), la longitud de los datos es 0*/
					continue;
				}
				
				final String nombre;
				final int pokedex_no;
				final String forma;
	
				if(hi[POKEDEX_NO]!=null && !data[hi[POKEDEX_NO]].isEmpty()){
					try {
						pokedex_no=Integer.parseInt(data[hi[POKEDEX_NO]]);
					}catch (NumberFormatException e) {
						LOG.warn("no se pudo manejar la entrada "+k+", el atributo obligatorio POKEDEX_NO no tiene el formato adecuado");
						continue;
					}
				}else {
					LOG.warn("no se pudo manejar la entrada "+k+", el atributo obligatorio POKEDEX_NO no se ha encontrado");
					continue;
				}
	
				if(hi[FORMA]!=null && !data[hi[FORMA]].isEmpty()){
					forma=data[hi[FORMA]];
				}else {
					LOG.warn("no se pudo manejar la entrada "+k+", el atributo obligatorio FORMA no se ha encontrado");
					continue;
				}
	
				if(hi[NOMBRE]!=null && !data[hi[NOMBRE]].isEmpty()){
					nombre=data[hi[NOMBRE]];
				}else {
					LOG.warn("no se pudo manejar la entrada "+k+", el atributo obligatorio NOMBRE no se ha encontrado");
					continue;
				}
	
				final PokemonDTO res = new PokemonDTO(pokedex_no, forma, nombre);
	
				if(hi[TIPO1]!=null && !data[hi[TIPO1]].isEmpty()) {
					try {
						res.setTipo1(Tipo.valueOf(data[hi[TIPO1]].toUpperCase()));
					}catch (IllegalArgumentException e) {
						LOG.warn("error procesando la entrada "+k+", atributo TIPO1 no se reconoce");
						continue;
					}
				}
				if(hi[TIPO2]!=null && !data[hi[TIPO2]].isEmpty()) {
					try {
						res.setTipo2(Tipo.valueOf(data[hi[TIPO2]].toUpperCase()));
					}catch (IllegalArgumentException e) {
						LOG.warn("error procesando la entrada "+k+", atributo TIPO2 no se reconoce");
						continue;
					}
				}
				if(hi[ATK]!=null && !data[hi[ATK]].isEmpty()) {
					try {
						res.setBase_ATK(Integer.parseInt(data[hi[ATK]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo ATK no es numerico");
						continue;
					}
				}
				if(hi[DEF]!=null && !data[hi[DEF]].isEmpty()) {
					try {
						res.setBase_DEF(Integer.parseInt(data[hi[DEF]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo DEF no es numerico");
						continue;
					}
				}
				if(hi[SPA]!=null && !data[hi[SPA]].isEmpty()) {
					try {
						res.setBase_SPA(Integer.parseInt(data[hi[SPA]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo SPA no es numerico");
						continue;
					}
				}
				if(hi[SPD]!=null && !data[hi[SPD]].isEmpty()) {
					try {
						res.setBase_SPD(Integer.parseInt(data[hi[SPD]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo SPD no es numerico");
						continue;
					}
				}
				if(hi[SPE]!=null && !data[hi[SPE]].isEmpty()) {
					try {
						res.setBase_SPE(Integer.parseInt(data[hi[SPE]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo ATK no es numerico");
						continue;
					}
				}
				if(hi[HP]!=null && !data[hi[HP]].isEmpty()) {
					try {
						res.setBase_HP(Integer.parseInt(data[hi[HP]]));
					}catch (NumberFormatException e) {
						LOG.warn("error procesando la entrada "+k+", atributo ATK no es numerico");
						continue;
					}
				}
	
				repository.save(res);
				updateProgress(k+1, k);
			}
		}catch(Exception ex) {
			LOG.error("error",ex);
			throw ex;
		}
		return null;
	}

	protected @Override void succeeded(){
		publisher.publishEvent(new ComponentDataRefreshEvent());
	}
	
	private Integer[] getHeaderIndexes(final String[] headers) {
		final Integer[] res = new Integer[_HEADERS];
		int k = 0;
		for(final String header: headers) {
			switch(header.trim()) {
			case "nombre":
			case "NOMBRE":
			case "name":
			case "NAME":
				res[NOMBRE] = k;
				break;

			case "forma":
			case "FORMA":
				res[FORMA] = k;
				break;

			case "pokedex_no":
			case "POKEDEX_NO":
			case "pokedex no":
			case "POKEDEX NO":
			case "n_pokedex":
			case "N_POKEDEX":
			case "n pokedex":
			case "N POKEDEX":
				res[POKEDEX_NO] = k;
				break;

			case "tipo_1":
			case "TIPO_1":
			case "tipo1":
			case "tipo 1":
				res[TIPO1] = k;
				break;

			case "tipo_2":
			case "TIPO_2":
			case "tipo2":
			case "tipo 2":
				res[TIPO2] = k;
				break;

			case "base_ATK":
			case "BASE_ATK":
			case "ATK":
				res[ATK] = k;
				break;		

			case "base_DEF":
			case "BASE_DEF":
			case "DEF":
				res[DEF] = k;
				break;

			case "base_SPA":
			case "BASE_SPA":
			case "SPA":
				res[SPA] = k;
				break;

			case "base_SPD":
			case "BASE_SPD":
			case "SPD":
				res[SPD] = k;
				break;

			case "base_SPE":
			case "BASE_SPE":
			case "SPE":
				res[SPE] = k;
				break;

			case "base_HP":
			case "BASE_HP":
			case "HP":
				res[HP] = k;
				break;

			default:
				LOG.info("unknown header ["+header+"]");
				// unknown header;
			}
			k++;
		}

		return res;
	}




}
