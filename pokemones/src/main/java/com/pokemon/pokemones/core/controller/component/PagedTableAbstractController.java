package com.pokemon.pokemones.core.controller.component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.pokemon.pokemones.core.item.dto.JobDTO;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public abstract class PagedTableAbstractController<E> extends AbstractController {

	protected int elements_per_page;
	protected int current_index;

	protected @FXML TableView<E> tableview;

	protected @FXML Pagination paginator;

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
		System.out.println("ir a "+i+",current index: "+current_index+":T="+Thread.currentThread().getId());
		//comprobar que la pagina no este fuera de rango si es conocido
		final int max = paginator.getMaxPageIndicatorCount();
		if(max!=Pagination.INDETERMINATE && i>=max) {
			/* estono deberia salir porque el paginador muestra solo los indices con contenido pero por si acaso */
			LOG.info("page requested out of range");
			tableview.setPlaceholder(new Text("page requested out of range"));
			tableview.getItems().clear();
			return tableview;
		}	

		// obtener elemetos nuevos
		final Page<E> page = getPage(i);
		final List<E> items = page.getContent();

		// comprobar indices y maximo
		current_index=i;
		
		paginator.setPageCount(page.getTotalPages());

		// devolver la vista con los nuevos elementos
		tableview.getItems().setAll(items);
		return tableview;	
	}

	protected abstract Page<E> getPage(final int i);

	protected void setElementsperPage(final int elements_per_page) {
		this.elements_per_page=elements_per_page;
	}

	public @Override void handleParams(Map<String, Object> args) {
		if(args.containsKey("index")) {
			current_index=(Integer)args.get("index");
			System.out.println("contiene: "+(Integer)args.get("index"));
		}
		setElementsperPage(3);//TODO cambiar esto a bien
		if(paginator.getPageFactory()==null) {
			paginator.setPageFactory(this::navigate);
		}
		refreshData();
	}	

	public @Override void refreshData() {	
		System.out.println("refrescando");
		this.paginator.setCurrentPageIndex(current_index);
	}


}
