package br.com.edsilfer.android.search_interface.model.xml;

import com.google.gson.Gson;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by efernandes on 23/12/16.
 */

@Root(name = "search-interface")
public class SearchInterface {
    @Attribute(name = "type")
    public String type;
    @Attribute(name = "name")
    public String name;
    @Attribute
    public String bkg;

    @ElementList(inline = true)
    public List<Component> component;

    public Boolean isBkgImage() {
        try {
            android.graphics.Color.parseColor(bkg);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public Component getComponentByType(String type) {
        for (Component c : component) {
            if (c.type.equals(type)) {
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

