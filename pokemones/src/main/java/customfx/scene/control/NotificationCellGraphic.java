package customfx.scene.control;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NotificationCellGraphic extends HBox{
		
		private @FXML Button closeButton;
		
		private @FXML Text texto;
		
		private @FXML Pane colorPane;

		public Button getCloseButton() {
			return closeButton;
		}

		public void setCloseButton(Button closeButton) {
			this.closeButton = closeButton;
		}
		
		public String getText() {
			return texto.getText();
		}

		public void setText(final String texto) {
			this.texto.setText(texto);
		}
		
		public void setColor(final Color color) {
			this.colorPane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		
}
