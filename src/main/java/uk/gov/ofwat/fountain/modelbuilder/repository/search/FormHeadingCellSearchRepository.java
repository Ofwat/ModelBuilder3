package uk.gov.ofwat.fountain.modelbuilder.repository.search;

import uk.gov.ofwat.fountain.modelbuilder.domain.FormHeadingCell;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FormHeadingCell entity.
 */
public interface FormHeadingCellSearchRepository extends ElasticsearchRepository<FormHeadingCell, Long> {
}
