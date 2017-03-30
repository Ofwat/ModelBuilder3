import { ModelAudit } from '../model-audit';
export class AuditChange {
    constructor(
        public id?: number,
        public changeText?: string,
        public modelAudit?: ModelAudit,
    ) { }
}
