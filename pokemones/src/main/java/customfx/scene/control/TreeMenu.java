package customfx.scene.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeMenu<T> extends TreeView<T> {

	public TreeMenu() {
		super();
		this.setCellFactory(e ->new ActionTreeCell<T>());
	}
	
	public void addEntry(final String path, final TreeItem<T> item) {
		final String[] rutas = path.trim().split("/");
		TreeItem<T> cursor = getRoot();
		for(final String r: rutas) {
			for(int k = 0; k < cursor.getChildren().size();k++) {
				if(cursor.getChildren().get(0).getValue().equals(r)) {
					cursor=cursor.getChildren().get(0);
					break;
				}
			}
			
		}
	}
	
	public void addEntry(final String path, final T elemento, final Node grafico, final EventHandler<ActionEvent> handler) {
		TreeItem<T> item = new ActionTreeItem<T>(elemento, grafico, handler);
		addEntry(path, item);
	}
	
	public void addEntry(final String path, final T elemento, final EventHandler<ActionEvent> handler) {
		TreeItem<T> item = new ActionTreeItem<T>(elemento,handler);
		addEntry(path, item);
	}
	
	
	
}
