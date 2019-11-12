package com.pokemon.pokemones.core.item.dto;

import javax.persistence.Column;
import javax.persistence.Id;

public class PropertyDPO implements ItemDPO<String> {

	private String k;
	private String v;
	
	@Id @Column
	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	@Column(nullable=false)
	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	@Override
	public String getPK() {
		return k;
	}

}
