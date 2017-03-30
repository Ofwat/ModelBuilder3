package uk.gov.ofwat.fountain.modelbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.ofwat.fountain.modelbuilder.domain.Item;
import uk.gov.ofwat.fountain.modelbuilder.domain.Model;
import uk.gov.ofwat.fountain.modelbuilder.domain.ModelItem;

/**
 * Created by jtoddington on 26/07/2016.
 */
public interface ModelItemRepository extends JpaRepository<ModelItem, Long>{
    ModelItem findByItemAndModel(Item item, Model model);
}
