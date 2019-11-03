package customfx.scene.control;

import com.pokemon.pokemones.core.event.NotificationEvent.Threat;

import javafx.scene.paint.Color;
public class NotificationUtils {

	public final static Color getColorFromThreat(final Threat threat) {
		switch (threat) {
			case ERROR:		return Color.RED;
			case SUCCESS: 	return Color.GREEN;
			case WARNING:	return Color.YELLOW;
			default:		return Color.BLUE;
		}
	}
	
}
