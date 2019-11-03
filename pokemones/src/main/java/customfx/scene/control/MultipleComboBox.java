package customfx.scene.control;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;

public class MultipleComboBox<E> extends ComboBox<E> {
	   
    protected @Override Skin<?> createDefaultSkin(){
		return new MultiSelectComboSkin<E>(this);
    }

	private final ObservableList<E> values;
	
	public ObservableList<E> getValues(){
			return values;
	}
	
//	public @Override E getValue(){
//		throw new UnsupportedOperationException();
//	}
//	
//	public @Override void setValue(final E value){
//		throw new UnsupportedOperationException();
//	}
//	
//	public @Override void setEditable(final boolean editable){
//		throw new UnsupportedOperationException();
//	}

	@SuppressWarnings("unchecked")
	public MultipleComboBox(){
		setEditable(false);
		
		values = FXCollections.observableArrayList();
		
		valueProperty().addListener((obs, nv, ov) -> {
			try {
			if(values.contains(nv)){
				values.remove(nv);
			}else{
				values.add(nv);
			}
//			try {
//				((ObjectProperty<E>)obs).set(null);
//			}catch (Exception e) {
//				System.err.println("error 1");
//				e.printStackTrace();
//			}
			}catch (Exception e) {
				System.err.println("error 2");
				e.printStackTrace();
			}
		});
	
		setCellFactory(l -> new MultipleSelectionListCell<E>(values));
	}


}