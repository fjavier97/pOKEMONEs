package customfx.scene.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class EnumerationComboBoxImpl<E extends Enum<?>> extends ComboBox<E> implements EnumerationComboBox<E>{
	
	private Class<E> data;
	
	public Class<E> getData() {
		return data;
	}
	
	public EnumerationComboBoxImpl() {
		super();
	}
	
	public EnumerationComboBoxImpl(final Class<E> enumerationClass) {
		this();
		setData(enumerationClass);
	}
	
	public void setData(final Class<E> enumerationClass) {
		data = enumerationClass;
		final ObservableList<E> items = FXCollections.observableArrayList(enumerationClass.getEnumConstants());
		items.add(null);
		super.setItems(items);
	}
	
	public EnumerationComboBoxImpl(final String enumerationClassName) throws ClassNotFoundException {
		this();
		setData(enumerationClassName);
	}
	
	public void setData(final String enumerationClassName) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		final Class<E> enumerationClass = (Class<E>) Class.forName(enumerationClassName);
		setData(enumerationClass);
	}
}
