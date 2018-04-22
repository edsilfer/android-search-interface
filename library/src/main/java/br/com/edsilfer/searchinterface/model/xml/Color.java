package br.com.edsilfer.searchinterface.model.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by efernandes on 23/12/16.
 */

public class Color {
    @Attribute
    public String id;
    @Attribute (required = false)
    public Float alpha;
    @Attribute (required = false)
    public Boolean isImage;

    @Text(data = true)
    public String value;
}
