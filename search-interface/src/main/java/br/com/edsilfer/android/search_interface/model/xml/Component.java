package br.com.edsilfer.android.search_interface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import br.com.edsilfer.kotlin_support.model.xml.Text;

/**
 * Created by efernandes on 23/12/16.
 */
@Root
public class Component {
    @Attribute(required = false)
    public String type;
    @Attribute(required = false)
    public String theme;
    @Attribute(required = false)
    public String display;

    @ElementList
    public List<Text> texts;
    @ElementList
    public List<Color> colors;
    @Element(required = false)
    public String disclaimer;

    public Text getTextByID(String id) {
        for (Text t : texts) {
            if (t.id.equals(id)) {
                return t;
            }
        }
        return null;
    }

    public Color getColorByID(String id) {
        for (Color c : colors) {
            if (c.id.equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
