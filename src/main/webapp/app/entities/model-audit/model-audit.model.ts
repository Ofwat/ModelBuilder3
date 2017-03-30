import { AuditDetails } from '../audit-details';
import { AuditChange } from '../audit-change';
import { Model } from '../model';
export class ModelAudit {
    constructor(
        public id?: number,
        public modelAuditDetail?: AuditDetails,
        public changes?: AuditChange,
        public model?: Model,
    ) { }
}
