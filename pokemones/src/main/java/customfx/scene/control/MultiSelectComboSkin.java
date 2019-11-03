package customfx.scene.control;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Skin;


@SuppressWarnings("restriction")
public class MultiSelectComboSkin<E> extends ComboBoxListViewSkin<E>
{
 
    public MultiSelectComboSkin(MultipleComboBox<E> comboBox)
    {
    super(comboBox);
 
    }
 
    protected boolean isHideOnClickEnabled()
    {
    return false;
    }
 
}