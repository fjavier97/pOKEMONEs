package customfx.scene.control;

import java.util.Observable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class EnumerationComboBox extends ComboBox<Enum<?>>{

	private final ObjectProperty<Class<Enum<?>>> data;
	
	public Class<Enum<?>> getData() {
		return data.get();
	}
	
	public ObjectProperty<Class<Enum<?>>> data() {
		return data;
	}
	
	public EnumerationComboBox() {
		super();
		this.data = new SimpleObjectProperty<Class<Enum<?>>>();
		data.addListener((o,v,n)->{
			final ObservableList<Enum<?>> items = FXCollections.observableArrayList(n.getEnumConstants());
			items.add(null);
			super.setItems(items);
		});
	}
	
	public EnumerationComboBox(final Class<Enum<?>> enumerationClass) {
		this();
		setData(enumerationClass);
	}
	
	public void setData(final Class<Enum<?>> enumerationClass) {
		data.set(enumerationClass);
	}
	
	public EnumerationComboBox(final String enumerationClassName) throws ClassNotFoundException {
		this();
		setData(enumerationClassName);
	}
	
	public void setData(final String enumerationClassName) throws ClassNotFoundException {
		@SuppressWarnings("unchecked")
		final Class<Enum<?>> enumerationClass = (Class<Enum<?>>) Class.forName(enumerationClassName);
		setData(enumerationClass);
	}
	
}
