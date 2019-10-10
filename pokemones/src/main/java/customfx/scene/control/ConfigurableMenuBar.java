package customfx.scene.control;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class ConfigurableMenuBar extends MenuBar{

	private List<Menu> core_menus;
	
	public ConfigurableMenuBar() {
		this.core_menus = new LinkedList<Menu>();
	}

	public List<Menu> getCoreMenus() {
		return core_menus;
	}

	public void addCoreMenu(Menu menu) {
		this.core_menus.add(menu);
	}
	
	public void addCoreMenus(List<Menu> menus) {
		this.core_menus.addAll(menus);
	}
	
	public List<Menu> getComponentMenus() {
		return getMenus();
	}

	public void addComponentMenu(Menu menu) {
		getMenus().add(menu);
	}
	
	public void addComponentMenus(List<Menu> menus) {
		getMenus().addAll(menus);
	}
	
	public void setMenus(List<Menu> menus) {
		getMenus().setAll(core_menus);
		getMenus().addAll(menus);
	}
	
}
