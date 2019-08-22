package customfx.scene.control;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class MenuDefinition {

	private final MenuItem menuitem;
	private String path;
	
	public MenuDefinition() {
		super();
		menuitem= new MenuItem();
	}
	
	public void setOnAction(final EventHandler<ActionEvent> value) {
		menuitem.setOnAction(value);
	}
	
	public EventHandler<ActionEvent> getOnAction() {
		return menuitem.getOnAction();
	}
	
	public void setText(final String text) {
		this.menuitem.setText(text);
	}
	
	public String getText() {
		return menuitem.getText();
	}
	
	public void setPath(final String path) {
		this.path=path;
	}
	
	public String getPath() {
		return path;
	}
	
	public BooleanProperty disableProperty() {
		return menuitem.disableProperty();
	}
	
	public MenuItem createMenuItem() {
		return menuitem;
	}
}
