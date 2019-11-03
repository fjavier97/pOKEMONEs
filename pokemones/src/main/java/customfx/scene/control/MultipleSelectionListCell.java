package customfx.scene.control;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MultipleSelectionListCell<E> extends ListCell<E>{
	
	public MultipleSelectionListCell(){
		super();
	}
	
	public MultipleSelectionListCell(final ObservableList<E> values){
		super();
		setValues(values);
	} 
	
	private ObservableList<E> values;
	
	public void setValues(final ObservableList<E> values){
		this.values = values;
	}
	
	public ObservableList<E> getValues(){
		return values;
	}
	
	public @Override void  updateItem(E item, boolean isEmpty){
		super.updateItem(item, isEmpty);
		if(item == null) {
			return;
		}else {
			setText(item.toString());
			if(this.getOnMouseClicked()==null) {
				this.setOnMouseClicked(e->updateItem(item, isEmpty));
			}
		}
		if(getValues() != null && getValues().contains(item)){
			setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));// seleccionado
		}else{
			setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(5))));// no seleccionado
		}
		
	}
}