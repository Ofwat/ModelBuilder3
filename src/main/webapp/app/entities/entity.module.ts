import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ModelBuilderModelModule } from './model/model.module';
import { ModelBuilderModelDetailsModule } from './model-details/model-details.module';
import { ModelBuilderTransferModule } from './transfer/transfer.module';
import { ModelBuilderTransferConditionModule } from './transfer-condition/transfer-condition.module';
import { ModelBuilderTransferBlockModule } from './transfer-block/transfer-block.module';
import { ModelBuilderTransferBlockDetailsModule } from './transfer-block-details/transfer-block-details.module';
import { ModelBuilderTransferBlockItemModule } from './transfer-block-item/transfer-block-item.module';
import { ModelBuilderModelAuditModule } from './model-audit/model-audit.module';
import { ModelBuilderAuditDetailsModule } from './audit-details/audit-details.module';
import { ModelBuilderAuditChangeModule } from './audit-change/audit-change.module';
import { ModelBuilderItemModule } from './item/item.module';
import { ModelBuilderYearModule } from './year/year.module';
import { ModelBuilderInputModule } from './input/input.module';
import { ModelBuilderHeadingModule } from './heading/heading.module';
import { ModelBuilderValidationRuleModule } from './validation-rule/validation-rule.module';
import { ModelBuilderValidationRuleDetailsModule } from './validation-rule-details/validation-rule-details.module';
import { ModelBuilderValidationRuleItemModule } from './validation-rule-item/validation-rule-item.module';
import { ModelBuilderCompanyPageModule } from './company-page/company-page.module';
import { ModelBuilderModelBuilderDocumentModule } from './model-builder-document/model-builder-document.module';
import { ModelBuilderPageModule } from './page/page.module';
import { ModelBuilderPageDetailsModule } from './page-details/page-details.module';
import { ModelBuilderSectionModule } from './section/section.module';
import { ModelBuilderSectionDetailsModule } from './section-details/section-details.module';
import { ModelBuilderLineModule } from './line/line.module';
import { ModelBuilderLineDetailsModule } from './line-details/line-details.module';
import { ModelBuilderCellRangeModule } from './cell-range/cell-range.module';
import { ModelBuilderCellModule } from './cell/cell.module';
import { ModelBuilderFormModule } from './form/form.module';
import { ModelBuilderFormDetailsModule } from './form-details/form-details.module';
import { ModelBuilderFormHeadingCellModule } from './form-heading-cell/form-heading-cell.module';
import { ModelBuilderFormCellModule } from './form-cell/form-cell.module';
import { ModelBuilderMacroModule } from './macro/macro.module';
import { ModelBuilderTextModule } from './text/text.module';
import { ModelBuilderTextBlockModule } from './text-block/text-block.module';
import { ModelBuilderYearCodeModule } from './year-code/year-code.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ModelBuilderModelModule,
        ModelBuilderModelDetailsModule,
        ModelBuilderTransferModule,
        ModelBuilderTransferConditionModule,
        ModelBuilderTransferBlockModule,
        ModelBuilderTransferBlockDetailsModule,
        ModelBuilderTransferBlockItemModule,
        ModelBuilderModelAuditModule,
        ModelBuilderAuditDetailsModule,
        ModelBuilderAuditChangeModule,
        ModelBuilderItemModule,
        ModelBuilderYearModule,
        ModelBuilderInputModule,
        ModelBuilderHeadingModule,
        ModelBuilderValidationRuleModule,
        ModelBuilderValidationRuleDetailsModule,
        ModelBuilderValidationRuleItemModule,
        ModelBuilderCompanyPageModule,
        ModelBuilderModelBuilderDocumentModule,
        ModelBuilderPageModule,
        ModelBuilderPageDetailsModule,
        ModelBuilderSectionModule,
        ModelBuilderSectionDetailsModule,
        ModelBuilderLineModule,
        ModelBuilderLineDetailsModule,
        ModelBuilderCellRangeModule,
        ModelBuilderCellModule,
        ModelBuilderFormModule,
        ModelBuilderFormDetailsModule,
        ModelBuilderFormHeadingCellModule,
        ModelBuilderFormCellModule,
        ModelBuilderMacroModule,
        ModelBuilderTextModule,
        ModelBuilderTextBlockModule,
        ModelBuilderYearCodeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ModelBuilderEntityModule {}
