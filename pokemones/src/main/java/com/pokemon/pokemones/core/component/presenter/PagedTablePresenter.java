package com.pokemon.pokemones.core.component.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

public class PagedTablePresenter<E> {

	protected @FXML TableView<E> tableview;

	protected @FXML Pagination paginator;

	public TableView<E> getTableview() {
		return tableview;
	}

	public Pagination getPaginator() {
		return paginator;
	}
	
}
