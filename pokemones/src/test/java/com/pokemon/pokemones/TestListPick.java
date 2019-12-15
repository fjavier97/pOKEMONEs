package com.pokemon.pokemones;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestListPick extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		StackPane sp = new StackPane();
		sp.setAlignment(Pos.BOTTOM_CENTER);
		
		BorderPane bdp = new BorderPane();
		bdp.setBackground(new Background(new BackgroundFill(Color.GREEN,CornerRadii.EMPTY,Insets.EMPTY)));
		bdp.setPickOnBounds(false);
		
		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setOrientation(Orientation.VERTICAL);
		
		StackPane auxp = new StackPane();		
		VBox box = new VBox();
		box.setAlignment(Pos.BOTTOM_CENTER);
		
		auxp.getChildren().add(box);
		auxp.setBackground(new Background(new BackgroundFill(Color.YELLOW,CornerRadii.EMPTY,Insets.EMPTY)));
		auxp.setPickOnBounds(false);
		box.setPickOnBounds(false);		
		box.setBackground(Background.EMPTY);
		box.setOnMouseClicked(e->System.out.println("nope"));		
		box.setSpacing(10);
		box.setPadding(new Insets(10));
		box.setAlignment(Pos.BOTTOM_CENTER);
		final int x = 20;
		for(int i = 0;i<x;i++) {
			box.getChildren().add(new Button(""+i));
			
		}
		
		bdp.setRight(scrollBar);
		bdp.setCenter(auxp);
		
		
		Pane p = new Pane();
		p.setOnMouseClicked(e->System.out.println("hola"));
		
		
		sp.getChildren().addAll(p,bdp);
		Scene s = new Scene(sp,300,300);
		primaryStage.setScene(s);
		primaryStage.show();
//		scrollBar.maxHeightProperty().bind(sp.heightProperty());
//		scrollBar.minHeightProperty().bind(sp.heightProperty());
//		scrollBar.setLayoutY((sp.getHeight()-scrollBar.getHeight())/2);
//		bdp.setClip(new Rectangle(sp.getWidth(),sp.getHeight()));
//		System.out.println("stack interno:\t"+auxp.getHeight()+"\nborderpane:\t"+bdp.getHeight()+
//				"\nstack externo\t"+sp.getHeight()+"\nscroll bar:\t"+scrollBar.getHeight());
		
	}
	
	public static void main(String[] args) {
		System.out.println("HI");
		Application.launch(args);
	}

}
