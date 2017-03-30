package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.ValidationRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ValidationRule entity.
 */
public interface ValidationRuleSearchRepository extends ElasticsearchRepository<ValidationRule, Long> {
}
