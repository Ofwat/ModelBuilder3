package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlockItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TransferBlockItem entity.
 */
public interface TransferBlockItemSearchRepository extends ElasticsearchRepository<TransferBlockItem, Long> {
}
