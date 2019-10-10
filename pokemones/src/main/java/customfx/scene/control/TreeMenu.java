package customfx.scene.control;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeMenu extends TreeView<String> {

	public TreeMenu() {
		super();
		setRoot(new ActionTreeItem<String>("root"));
		setShowRoot(false);
		setCellFactory(e ->new ActionTreeCell<String>());
	}
	
	public void addEntry(final String path, final TreeItem<String> item) {
		/* hago split de la ruta */
		String[] rutas = path.trim().split("/");
		if(rutas.length>1) {
			rutas=Arrays.copyOfRange(rutas, 1, rutas.length);
		}
		
		/* creo cursor para recorrer el arbor y lo situo en la raiz */
		TreeItem<String> cursor = getRoot();
		
		/* recorro la ruta especificada buscando los nos en el arbol */
		for(final String r: rutas) {
			
			/* busco entre los hijos del nodo actual el hijo siguiente en la ruta*/
			boolean encontrado=false;
			for(int k = 0; k < cursor.getChildren().size() && !encontrado ; k++) {
				
				/* si lo encuentro avanzo el cursor el */
				if(cursor.getChildren().get(k).getValue().equals(r)) {
					cursor=cursor.getChildren().get(k);
					encontrado=true;
					break;
				}
				
			}			
			/* si no he encontrado la ruta la creo y avanzo el cursor a el*/
			if(!encontrado) {
				ActionTreeItem<String> nuevo = new ActionTreeItem<String>(r);
				cursor.getChildren().add(nuevo);
				cursor=nuevo;	
			}
		}
		
		/* cuando encuentro el lugar inserto el nodo que me han pasado como argumento */
		cursor.getChildren().add(item);
	}
	
	public void addEntry(final String path, final String elemento, final Node grafico, final EventHandler<ActionEvent> handler) {
		TreeItem<String> item = new ActionTreeItem<String>(elemento, grafico, handler);
		addEntry(path, item);
	}
	
	public void addEntry(final String path, final String elemento, final EventHandler<ActionEvent> handler) {
		TreeItem<String> item = new ActionTreeItem<String>(elemento,handler);
		addEntry(path, item);
	}
		
}
