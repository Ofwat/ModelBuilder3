package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.ModelAudit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ModelAudit entity.
 */
public interface ModelAuditSearchRepository extends ElasticsearchRepository<ModelAudit, Long> {
}
