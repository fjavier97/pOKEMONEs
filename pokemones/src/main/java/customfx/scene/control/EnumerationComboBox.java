package customfx.scene.control;

public interface EnumerationComboBox<E extends Enum<?>>{
	
	/* se recuerda que se necesita un constructor sin argumentos, y se aconsejan otros que seteen las cosas */
	
	/* en esta variable se guarda la clase */
	//private  Class<E> data;
	
	/* consultar la clase */
	public Class<E> getData();
	
	/* en los setters añadir elemento nulo para entradas vacias */
	
	/* especificar la clase (establece las opciones a elegir) */
	public void setData(final Class<E> enumerationClass);
	
	/* especificar la clase a traves de su nombre (establece las opciones a elegir) */
	public void setData(final String enumerationClassName) throws ClassNotFoundException;

}
