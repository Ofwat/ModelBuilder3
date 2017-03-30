package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.CellRange;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CellRange entity.
 */
public interface CellRangeSearchRepository extends ElasticsearchRepository<CellRange, Long> {
}
