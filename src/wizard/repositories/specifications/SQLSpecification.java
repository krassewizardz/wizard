package wizard.repositories.specifications;

import wizard.repositories.Specification;

/**
 * Created by jansziegaud on 22.06.17.
 */

public interface SQLSpecification extends Specification {
    public String toSQLQuery();
}
