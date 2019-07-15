package recursos.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="componente")
public class Componente {

	private String nombre;
	private String localizacion;
	private String component;
	
	@XmlAttribute(name="nombre",required=true)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlElement(name="menu_path", required=false, defaultValue="/")
	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	@XmlAttribute(name="class_template", required=true)
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public static void main(String[] args) {

//		System.out.println("---");
//		for(String s: "/file/edit".split("/")) {
//			if(!s.isEmpty()) {
//				System.out.println("|"+s+"|");
//			}
//		}
//		System.out.println("---");
	}
}
