package customfx.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class ActionTreeItem<T> extends TreeItem<T> {

	private EventHandler<ActionEvent> handler;
	
	public ActionTreeItem(){
		super();
		this.setHandler(null);
	}
	
	public ActionTreeItem(final EventHandler<ActionEvent> handler){
		super();
		this.setHandler(handler);
	}
	
	public ActionTreeItem(final T contenido){
		super(contenido);
		this.setHandler(null);
	}
	
	public ActionTreeItem(final T contenido, final EventHandler<ActionEvent> handler){
		super(contenido);
		this.setHandler(handler);
	}
	
	public ActionTreeItem(final T contenido, final Node grafico, final EventHandler<ActionEvent> handler){
		super(contenido, grafico);
		this.setHandler(handler);
	}
	
	public ActionTreeItem(final T contenido, final Node grafico){
		super(contenido, grafico);
		this.setHandler(null);
	}

	public EventHandler<ActionEvent> getHandler() {
		return handler;
	}

	public void setHandler(EventHandler<ActionEvent> handler) {
		this.handler = handler;
	}
	
	
}
