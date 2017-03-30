package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.SectionDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SectionDetails entity.
 */
public interface SectionDetailsSearchRepository extends ElasticsearchRepository<SectionDetails, Long> {
}
