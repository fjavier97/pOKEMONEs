package customfx.scene.control;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
/**
 * para ver los elementos, enlazar la lista [values] con un listview.
 * en FXML: 
 * <ListView fx:id="id_lista" items="$p.values"/>
 * @author DAVID
 *
 * @param <T>
 */
public class MultiSelectionComboBox<T> extends ComboBox<T>{

	//DAVID APROVES
	
	private ObservableList<T> values;
	
	public ObservableList<T> getValues(){
		return values;
	}

	public MultiSelectionComboBox() {
		super();
		this.values=FXCollections.observableArrayList();
		valueProperty().addListener((obs, ov, nv) ->{
			if(nv!=null) {
				if(values.contains(nv)) {
					values.remove(nv);
				}else {
					values.add(nv);
				}
				Platform.runLater(()->setValue(null));
			}
		});
	}
}
