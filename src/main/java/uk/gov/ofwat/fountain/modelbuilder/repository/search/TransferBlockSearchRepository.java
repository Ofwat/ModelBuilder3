package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.TransferBlock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TransferBlock entity.
 */
public interface TransferBlockSearchRepository extends ElasticsearchRepository<TransferBlock, Long> {
}
