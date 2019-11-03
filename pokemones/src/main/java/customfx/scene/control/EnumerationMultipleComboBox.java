package customfx.scene.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EnumerationMultipleComboBox<E extends Enum<?>> extends MultipleComboBox<E> implements EnumerationComboBox<E>{
	   
   	private Class<E> data;
	
	public Class<E> getData() {
		return data;
	}
	
	public EnumerationMultipleComboBox() {
		super();
	}
	
	public EnumerationMultipleComboBox(final Class<E> enumerationClass) {
		this();
		setData(enumerationClass);
	}
	
	public void setData(final Class<E> enumerationClass) {
		data = enumerationClass;
		final ObservableList<E> items = FXCollections.observableArrayList(enumerationClass.getEnumConstants());
		items.add(null);
		super.setItems(items);
	}
	
	public EnumerationMultipleComboBox(final String enumerationClassName) throws ClassNotFoundException {
		this();
		setData(enumerationClassName);
	}
	
	public void setData(final String enumerationClassName) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		final Class<E> enumerationClass = (Class<E>) Class.forName(enumerationClassName);
		setData(enumerationClass);
	} 
}