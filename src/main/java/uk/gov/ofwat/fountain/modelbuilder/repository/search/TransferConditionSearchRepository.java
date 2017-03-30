package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferCondition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TransferCondition entity.
 */
public interface TransferConditionSearchRepository extends ElasticsearchRepository<TransferCondition, Long> {
}
