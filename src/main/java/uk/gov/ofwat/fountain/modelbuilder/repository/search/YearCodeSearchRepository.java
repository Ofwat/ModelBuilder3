package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.YearCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the YearCode entity.
 */
public interface YearCodeSearchRepository extends ElasticsearchRepository<YearCode, Long> {
}
