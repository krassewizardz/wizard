package wizard.utility;

/**
 *  @author F. Engels
 *  TODO@all: description
 */
public class QueryParam <ValueType> {

    public String name = null;
    public ValueType value = null;

    public QueryParam(String name, ValueType value) {
        this.name = name;
        this.value = value;
    }

}
