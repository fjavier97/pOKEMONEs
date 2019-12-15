package customfx.scene.control;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EnumerationMultiSelectionComboBox<E extends Enum<?>> extends MultiSelectionComboBox<E> {
	   
   	public EnumerationMultiSelectionComboBox(@NamedArg("class") final Class<E> clazz) {
   		super();
   		setItems(FXCollections.observableArrayList(clazz.getEnumConstants()));
   	}
   	
 	public EnumerationMultiSelectionComboBox(@NamedArg("classname") final String classname) {
   		super();
   		try {
			setItems((ObservableList<E>) FXCollections.observableArrayList(Class.forName(classname).getEnumConstants()));
		} catch (ClassNotFoundException e) {}
   	}
   	
}