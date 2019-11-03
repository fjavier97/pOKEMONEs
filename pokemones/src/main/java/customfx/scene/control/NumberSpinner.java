package customfx.scene.control;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

public class NumberSpinner extends Spinner<Integer>{
	
	public NumberSpinner(){
		super();
		
		setEditable(true);
		

//			/* cuando el texto adquiere focus se selecciona todo para uqe al escribir algo se borre lo anterior */
//		getEditor().focusedProperty().addListener((obs,ov,nv)->{//changeListener
//			if(nv){
//				/* he probado a seleccionarlo pero suda polla */
//				this.getEditor().setText("");
//			}else {
//				if(getEditor().getText().isEmpty()) {
//					getEditor().setText("0");
//				}
//				commitEditorChanges();
//			}
//		});
//		
//		
		/* listener que escucha cambios en el editor y los guarda en el valor del spinner */
		getEditor().textProperty().addListener((obs, ov, nv)->{//invalidationListener
			if(!getEditor().getText().isEmpty()) {
				increment(0);
			}
		});	
		
	}
}