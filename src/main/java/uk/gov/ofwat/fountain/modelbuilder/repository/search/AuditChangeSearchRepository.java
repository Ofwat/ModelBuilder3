package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.AuditChange;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuditChange entity.
 */
public interface AuditChangeSearchRepository extends ElasticsearchRepository<AuditChange, Long> {
}
