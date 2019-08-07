package customfx.scene.control;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class ConfigurableMenuBar extends MenuBar{

	private final Map<String,Menu> systemMenus;
	
	public ConfigurableMenuBar() {
		super();
		systemMenus= new HashMap<String, Menu>();
	}
	
	public void clean() {
		this.getMenus().clear();
		for(String k : systemMenus.keySet()) {
			getMenus().add(systemMenus.get(k));
		}
	}
	
	private Menu getSubMenu(final ObservableList<? extends MenuItem> menus, final String name){
		for(MenuItem m: menus) {
			if(systemMenus.containsKey(name)) {
				return null;//Excepcion
			}
			if(m.getText().equals(name)) {
				try {
					return Menu.class.cast(m);
				}catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}
	
	public void addMenu(MenuDefinition menu) throws MenuCretionException {
		/* hago split de la ruta */
		String[] rutas = menu.getPath().trim().split("/");
		if(rutas.length>1) {
			rutas=Arrays.copyOfRange(rutas, 1, rutas.length);
		}
		
		if(systemMenus.containsKey(rutas[0])) {
			throw new MenuCretionException();
		}
		
		/* creo cursor para recorrer el arbor */
		Menu cursorPadre=null, cursorHijo=null;
		
		/* recorro la ruta especificada buscando los nos en el arbol */
		for(final String r: rutas) {			
			/* avanzo el cursor */
			cursorHijo = getSubMenu(cursorPadre==null?getMenus():cursorPadre.getItems(), r);
			if(cursorHijo==null) {
				cursorHijo=new Menu(r);
					if(cursorPadre==null) {
						getMenus().add(cursorHijo);
					}else {
						cursorPadre.getItems().add(cursorHijo);
					}

			}
			cursorPadre=cursorHijo;
		}		
		cursorPadre.getItems().add(menu.createMenuItem());
	}
	
	public void addSystemMenu(MenuDefinition sysmenu) throws MenuCretionException{
		/* hago split de la ruta */
		String[] rutas = sysmenu.getPath().trim().split("/");
		if(rutas.length>1) {
			rutas=Arrays.copyOfRange(rutas, 1, rutas.length);
		}
		
		/* creo cursor para recorrer el arbor */
		Menu cursorPadre=systemMenus.get(rutas[0]);
		if(cursorPadre==null) {
			//throw new MenuCretionException();
			registerSystemMenu(rutas[0]);
			cursorPadre=systemMenus.get(rutas[0]);
		}
		Menu cursorHijo=null;
		
		/* recorro la ruta especificada buscando los nos en el arbol */
		for(int k=1;k<rutas.length;k++) {			
			/* avanzo el cursor */
			cursorHijo = getSubMenu(cursorPadre.getItems(), rutas[k]);			
			if(cursorHijo==null) {
				cursorHijo=new Menu(rutas[k]);
				cursorPadre.getItems().add(cursorHijo);
			}
			System.out.print(cursorHijo.getText()+"-");
			cursorPadre=cursorHijo;
			System.out.println(cursorHijo.getText());
		}		
		cursorPadre.getItems().add(sysmenu.createMenuItem());
	}
	
	public void registerSystemMenu(String sysmenuname) {
		if(!systemMenus.containsKey(sysmenuname)) {
			final Menu sysmenu = new Menu(sysmenuname);
			systemMenus.put(sysmenuname, sysmenu);
		}		
	}
}
