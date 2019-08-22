package com.pokemon.pokemones.item.enums;

import java.util.HashMap;
import java.util.Map;

public enum Tipo {

	PLANTA,
	BICHO,
	FUEGO,
	ELECTRICO,
	AGUA,
	TIERRA,
	ROCA,
	ACERO,
	DRAGON,
	HADA,
	FANTASMA,
	SINIESTRO,
	PSIQUICO,
	VENENO,
	HIELO,
	NORMAL,
	LUCHA,
	VOLADOR;
	
	
    private static Map<Integer, Tipo> map = new HashMap<>();


    static {
        for (Tipo t : Tipo.values()) {
            map.put(t.ordinal(), t);
        }
    }
    
    public static Tipo valueOf(int tipo) {
        return (Tipo) map.get(tipo);
    }
	
}
