package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRuleDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ValidationRuleDetails entity.
 */
public interface ValidationRuleDetailsSearchRepository extends ElasticsearchRepository<ValidationRuleDetails, Long> {
}
