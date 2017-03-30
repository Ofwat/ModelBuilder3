package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuditDetails entity.
 */
public interface AuditDetailsSearchRepository extends ElasticsearchRepository<AuditDetails, Long> {
}
