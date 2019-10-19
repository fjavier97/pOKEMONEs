package com.pokemon.pokemones.item.dto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.item.pk.PokemonPK;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



@IdClass(PokemonPK.class)
@Entity @Table(name="Pokemon") @XmlRootElement(name="Pokemon")
public class PokemonDTO {

	private IntegerProperty pokedexNoProperty;
	private StringProperty formaProperty;	
	private StringProperty nombreProperty;
	
	private ObjectProperty<Tipo> tipo1Property;
	private ObjectProperty<Tipo> tipo2Property;
	
	private IntegerProperty baseATKProperty;
	private IntegerProperty baseDEFProperty;
	private IntegerProperty baseSPAProperty;
	private IntegerProperty baseSPDProperty;
	private IntegerProperty baseSPEProperty;
	private IntegerProperty baseHPProperty;
	
	public PokemonDTO() {
		this.pokedexNoProperty = new SimpleIntegerProperty(this,"pokedexNo");
		this.formaProperty = new SimpleStringProperty(this,"forma");
		this.nombreProperty = new SimpleStringProperty(this,"nombre");
		
		this.tipo1Property = new SimpleObjectProperty<>(this,"tipo1");
		this.tipo2Property = new SimpleObjectProperty<>(this,"tipo2");
		
		this.baseATKProperty = new SimpleIntegerProperty(this,"baseATK");
		this.baseDEFProperty = new SimpleIntegerProperty(this,"baseDEF");
		this.baseSPAProperty = new SimpleIntegerProperty(this,"baseSPA");
		this.baseSPDProperty = new SimpleIntegerProperty(this,"baseSPD");
		this.baseSPEProperty = new SimpleIntegerProperty(this,"baseSPE");
		this.baseHPProperty = new SimpleIntegerProperty(this,"baseHP");		
	}
	
	public PokemonDTO(final int pokedexNo, final String forma, final String nombre) {
		this();
		setPokedexNo(pokedexNo);
		setForma(forma);
		setNombre(nombre);
	}

	public  @Transient PokemonPK getPK() {
		return new PokemonPK(getPokedexNo(),getForma());
	}
	
	/* numero de la pokedex */
	public final IntegerProperty pokedexNoProperty() {
		return this.pokedexNoProperty;
	}	

	public @Id @Column final int getPokedexNo() {
		return this.pokedexNoProperty().get();
	}

	public final void setPokedexNo(final int pokedexNoProperty) {
		this.pokedexNoProperty().set(pokedexNoProperty);
	}
	

	/* forma */
	public final StringProperty formaProperty() {
		return this.formaProperty;
	}	

	public @Id @Column final String getForma() {
		return this.formaProperty().get();
	}	

	public final void setForma(final String formaProperty) {
		this.formaProperty().set(formaProperty);
	}
	

	/* nombre */ 
	public final StringProperty nombreProperty() {
		return this.nombreProperty;
	}
	
	public @Column final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombreProperty) {
		this.nombreProperty().set(nombreProperty);
	}
	

	/* tipo 1 */
	public final ObjectProperty<Tipo> tipo1Property() {
		return this.tipo1Property;
	}	

	public @Column final Tipo getTipo1() {
		return this.tipo1Property().get();
	}	

	public final void setTipo1(final Tipo tipo1Property) {
		this.tipo1Property().set(tipo1Property);
	}
	

	/* tipo 2 */
	public final ObjectProperty<Tipo> tipo2Property() {
		return this.tipo2Property;
	}	

	public @Column final Tipo getTipo2() {
		return this.tipo2Property().get();
	}	

	public final void setTipo2(final Tipo tipo2Property) {
		this.tipo2Property().set(tipo2Property);
	}
	

	/* atque base */
	public final IntegerProperty baseATKProperty() {
		return this.baseATKProperty;
	}	

	public @Column final int getBaseATK() {
		return this.baseATKProperty().get();
	}	

	public final void setBaseATK(final int baseATKProperty) {
		this.baseATKProperty().set(baseATKProperty);
	}
	

	/* defensa base */
	public final IntegerProperty baseDEFProperty() {
		return this.baseDEFProperty;
	}	

	public @Column final int getBaseDEF() {
		return this.baseDEFProperty().get();
	}	

	public final void setBaseDEF(final int baseDEFProperty) {
		this.baseDEFProperty().set(baseDEFProperty);
	}
	

	/* ataque especial base */
	public final IntegerProperty baseSPAProperty() {
		return this.baseSPAProperty;
	}	

	public @Column final int getBaseSPA() {
		return this.baseSPAProperty().get();
	}	

	public final void setBaseSPA(final int baseSPAProperty) {
		this.baseSPAProperty().set(baseSPAProperty);
	}
	

	/* defensa especial base */
	public final IntegerProperty baseSPDProperty() {
		return this.baseSPDProperty;
	}	

	public @Column final int getBaseSPD() {
		return this.baseSPDProperty().get();
	}	

	public final void setBaseSPD(final int baseSPDProperty) {
		this.baseSPDProperty().set(baseSPDProperty);
	}
	

	/* velocidad base */
	public final IntegerProperty baseSPEProperty() {
		return this.baseSPEProperty;
	}

	public @Column final int getBaseSPE() {
		return this.baseSPEProperty().get();
	}
	
	public final void setBaseSPE(final int baseSPEProperty) {
		this.baseSPEProperty().set(baseSPEProperty);
	}	

	/* vida base */
	public final IntegerProperty baseHPProperty() {
		return this.baseHPProperty;
	}	

	public @Column final int getBaseHP() {
		return this.baseHPProperty().get();
	}	

	public final void setBaseHP(final int baseHPProperty) {
		this.baseHPProperty().set(baseHPProperty);
	}	
}
