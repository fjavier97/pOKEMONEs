package customfx.scene.control;

import javafx.scene.control.TreeView;

public class TreeMenu<T> extends TreeView<T> {

	public TreeMenu() {
		super();
		this.setCellFactory(e ->new ActionTreeCell<T>());
	}
	
}
