package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.PageDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PageDetails entity.
 */
public interface PageDetailsSearchRepository extends ElasticsearchRepository<PageDetails, Long> {
}
