package customfx.scene.control;

import javafx.beans.NamedArg;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListSizeValueFactory<S> extends  PropertyValueFactory<S, Number>{

	public ListSizeValueFactory(final @NamedArg("property") String property) {
		super(property);
		System.out.println("-----hola");
	}
	
	@Override
	public ObservableValue<Number> call(CellDataFeatures<S, Number> param) {
		try {
			/* super.call() devuelve un ObjectWrapper que engloba el ObservableList. */
			/* Hay que engañar con el ? porque teroricamente es un Number y salta un classcast si pedimos un ObservableList directamente */
			final ObservableValue<?> res = super.call(param);
			return Bindings.size((ObservableList<?>)res.getValue());
		}catch (Exception e) {
			return null;
		}
		
	}

}
