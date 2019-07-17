package com.pokemon.pokemones;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MenuTreeCell<E> extends TreeCell<E>{
	
	private double arrow_size;
		
	public MenuTreeCell(){
		this(7);
	}
	
	public MenuTreeCell(final double arrow_size){
		super();
		this.arrow_size=arrow_size;
		if(getPrefHeight()<=0) {
			setPrefHeight(50);
		}
		
	}
	
    @Override
    protected void updateItem(E item, boolean empty) {    
    	
        super.updateItem(item, empty);
        
        this.setStyle("-fx-background-color:purple;");
        
        ((Region)getDisclosureNode()).prefHeightProperty().bind(this.heightProperty());
        // npi de por que es /3, deberia ser /2, pero no funciona y asi si.
        final String st = "-fx-padding:"+((getPrefHeight()-arrow_size)/3)+" 10 "+((getPrefHeight()-arrow_size)/2)+" 10;";
		getDisclosureNode().setStyle(st);

        if (isEmpty()) {
            setGraphic(null);
            setText(null);
        } else {
        	
        	Labeled btn;
        	
        	if(getTreeItem() instanceof ActionTreeItem) {        		
				btn = new Button(item.toString());
				((Button)btn).setOnAction(((ActionTreeItem<E>)getTreeItem()).getHandler());			
        	}else {        		
				btn = new Label(item.toString());				
        	}
        	setText(null);
    		
			final HBox box = new HBox();			

			btn.setAlignment(Pos.BASELINE_LEFT);
			btn.setMaxWidth(Double.MAX_VALUE);
			btn.setMaxHeight(Double.MAX_VALUE);
			HBox.setHgrow(btn, Priority.ALWAYS);
			
			btn.setGraphic(getTreeItem().getGraphic());				
			
			box.getChildren().add(btn);
			
			setGraphic(box);
        }
    }
}
