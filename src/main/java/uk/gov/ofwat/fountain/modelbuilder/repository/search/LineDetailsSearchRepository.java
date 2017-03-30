package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.LineDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LineDetails entity.
 */
public interface LineDetailsSearchRepository extends ElasticsearchRepository<LineDetails, Long> {
}
