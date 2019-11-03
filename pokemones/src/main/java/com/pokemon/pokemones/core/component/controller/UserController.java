package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.component.presenter.PokemonEditorPresenter;
import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.presenter.UserPresenter;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.services.SecurityUserService;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.pk.PokemonPK;
import com.pokemon.pokemones.service.PokemonService;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;


@Component("User")
@Scope("ComponentScope")
public class UserController extends AbstractController<UserPresenter>{
			
		private final ObjectProperty<UserDPO> modelo;
		private final BooleanProperty creating;
		
		private final UserDPO getModel() {
			return modelo.get();
		}
		
		private final void setModel(final UserDPO newmodel) {
			modelo.set(newmodel);
		}
		
		private final boolean isCrationMode() {
			return creating.get();
		}
		
		private final void setCreationMode(final boolean mode) {
			creating.set(mode);
		}
		
		private final SecurityUserService userService;
		
		private @Autowired UserController(final SecurityUserService userService){
			this.userService = userService;

			creating = new SimpleBooleanProperty(true);
			creating.addListener((obs, ov, nv)->{	
				/* settear a disable lo que no puede cambiarse */
			});
			
			modelo=new SimpleObjectProperty<>();
			modelo.addListener((obs, ov, nv)->{			
				LOG.info("model changed, loading bindings");	
				if(ov!=null) {
					unbind(ov);
				}
				bind(nv);			
			});
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected final /*@Override*/ void bind(final UserDPO model){

		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected final /*@Override*/ void unbind(final UserDPO model){
					

		}
		
		
		/* operaciones */	
		
		private @FXML void atras(){
			final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("Users", Navigation.BACKWARD);
			publisher.publishEvent(evt);
		}
		
		private @FXML void guardar() {	
			try {
				userService.save(modelo.get());					
				publisher.publishEvent(new NotificationEvent("usuario guardado", Threat.SUCCESS));
				setCreationMode(false);
			}catch (AccessDeniedException e1) {
				publisher.publishEvent(new NotificationEvent("no tiene la autoridad para realizar esta accion", Threat.WARNING));
			}catch (Exception e2) {
				e2.printStackTrace();
				publisher.publishEvent(new NotificationEvent("error al guardar: "+e2.getMessage(), Threat.ERROR));
			}
					
		}

		private @FXML void refrescar() {
			//setModel(isCrationMode()?userService.create():userService.findById(getModel().getPK()));
		}
		
		private @FXML void nuevo() {
			setCreationMode(true);
			setModel(userService.create());
		}
		
		
		/* metodos heredados */
		public @Override void handleParams(Map<String, Object> args) {		
			
			setCreationMode(!args.containsKey("model"));
			
			modelo.set(isCrationMode()?pokemonService.create():(PokemonDTO)args.get("model"));

			
			if(!isCrationMode())
				LOG.info("containing model [id="+modelo.get().getPokedexNo()+",forma="+modelo.get().getForma()+"]");
			
			LOG.info("openenig editor in "+(isCrationMode()?"creating":"editing")+" mode");	
		}
		
		public @Override void refreshData() {
			this.modelo.set(userService.findById(pk));
		}

		@Override
		protected UserPresenter initPresenter() {
			return new UserPresenter();
		}
			
//		private void clearInputs() {
//			getPresenter().getInput_pokedex_n().getValueFactory().setValue(0);
//			getPresenter().getInput_nombre().clear();
//			getPresenter().getInput_forma().clear();
//			getPresenter().getInput_tipo_1().setValue(null);
//			getPresenter().getInput_tipo_2().setValue(null);		
//			getPresenter().getInput_ATK().getValueFactory().setValue(0);
//			getPresenter().getInput_DEF().getValueFactory().setValue(0);
//			getPresenter().getInput_SPA().getValueFactory().setValue(0);
//			getPresenter().getInput_SPD().getValueFactory().setValue(0);
//			getPresenter().getInput_SPD().getValueFactory().setValue(0);
//			getPresenter().getInput_HP().getValueFactory().setValue(0);
//		}
	

}
