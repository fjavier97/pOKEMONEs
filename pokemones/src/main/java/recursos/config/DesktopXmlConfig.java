package recursos.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="configuracion_escritorio")
public class DesktopXmlConfig {

	private List<Componente> componentes;

	@XmlElementWrapper(name="componentes")
	@XmlElement(name="componente")
	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}
	
	public static void main(String[] args) {
		
	}	
	
}
