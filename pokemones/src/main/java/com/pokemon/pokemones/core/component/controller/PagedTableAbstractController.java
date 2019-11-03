package com.pokemon.pokemones.core.component.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.jpa.domain.Specification;

import com.pokemon.pokemones.core.component.presenter.PagedTablePresenter;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public abstract class PagedTableAbstractController<E, P extends PagedTablePresenter<E>> extends AbstractController<P> {

	/*
		setPageCount() es el numero total de paginas
		setMaxPageIndicatorCount() es el numero botones que aparecen abajo para ir a la otra pagina
	 */
	
	protected abstract SpecificationExecutor<E> getRepo(); 
	
	protected int elements_per_page;
	protected int current_index;

	public PagedTableAbstractController() {
		super();
		current_index=0;
	}

	/**
	 * este metodo se tiene que encargar de vaciar la tabla y pedir y cargar los itemspertenecientes a la pagina i.
	 * Adicionalmente deberaactualizar el numero maximo de paginas que existen. 
	 * @param i, indice de la pagina solicitada
	 * @return
	 */
	protected Node navigate(final int i) {
		final int indice = i==-1?current_index:i;
		System.out.println("ir a "+indice+",current index: "+current_index+":T="+Thread.currentThread().getId());
		
		//comprobar que la pagina no este fuera de rango si es conocido
		final int max = getPresenter().getPaginator().getPageCount();
		if(max!=Pagination.INDETERMINATE && indice>=max) {
			/* esto no deberia salir porque el paginador muestra solo los indices con contenido pero por si acaso */
			LOG.info("page requested out of range");
			getPresenter().getTableview().setPlaceholder(new Text("page requested out of range"));
			getPresenter().getTableview().getItems().clear();
			return getPresenter().getTableview();
		}
		/* preparamos el filtro */
		List<Specification<E>> filtro = new LinkedList<Specification<E>>();
		provideFilters(filtro);
		
		final Page<E> page = getRepo().findAll(specifications(filtro),PageRequest.of(indice, elements_per_page));

		// obtener elemetos nuevos
		final List<E> items = page.getContent();

		// comprobar indices y maximo
		if(i!=-1) {
			current_index=indice;
		}
		
		if(getPresenter().getPaginator().getPageCount()!=page.getTotalPages()) {
			getPresenter().getPaginator().setPageCount(page.getTotalPages());
		}


		// devolver la vista con los nuevos elementos
		getPresenter().getTableview().getItems().setAll(items);
		return getPresenter().getTableview();	
	}

	protected abstract void provideFilters(final List<Specification<E>> especificaciones);
	
	protected void setElementsperPage(final int elements_per_page) {
		this.elements_per_page=elements_per_page;
	}

	public @Override void handleParams(Map<String, Object> args) {
		if(args.containsKey("index")) {
			current_index=(Integer)args.get("index");
			System.out.println("contiene: "+(Integer)args.get("index"));
		}
		System.out.println(getPresenter().getPaginator()==null);
		getPresenter().getPaginator().setMaxPageIndicatorCount(7);
		setElementsperPage(3);//TODO cambiar esto a bien
		
		if(getPresenter().getPaginator().getPageFactory()==null) {
			getPresenter().getPaginator().setPageFactory(this::navigate);
		}
		
		refreshData();
	}	

	public @FXML @Override void refreshData() {	
		/* llamo a navigate porque si no al parecer suda polla de recargar la tabla si el indice ya es el mismo */
		navigate(-1);
		/* llamo a setcurrentindex porque si no al cambiar y volver del componente pilla el 0 */
		getPresenter().getPaginator().setCurrentPageIndex(current_index);
	}
	
	private Specification<E> specifications(List<Specification<E>> specs){
		if(specs == null || specs.size() == 0) return null;
		Specification<E> res = null;
		int i, j;
		/* busco el primer elemento no nulo */
		for(i = 0; i < specs.size() && res == null; i++){
			if(specs.get(i) != null) {
				res = specs.get(i);
				break;
			}
		}
		if(res==null) return null;
		/*itero sobre el resto aplicanod un AND (la propia operacion soporta pasarle argumentos nulos)*/
		for(j = i; j < specs.size(); j++){
			res = res.and(specs.get(j));
		}
		return res;
	}


}
