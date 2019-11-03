package customfx.scene.control;

import com.pokemon.pokemones.core.event.NotificationEvent;

public interface NotificationRenderer{
	public NotificationCellGraphic renderNotification(final NotificationEvent item);
}