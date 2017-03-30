import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModelAudit } from './model-audit.model';
import { ModelAuditService } from './model-audit.service';

@Component({
    selector: 'jhi-model-audit-detail',
    templateUrl: './model-audit-detail.component.html'
})
export class ModelAuditDetailComponent implements OnInit, OnDestroy {

    modelAudit: ModelAudit;
    private subscription: any;

    constructor(
        private modelAuditService: ModelAuditService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.modelAuditService.find(id).subscribe(modelAudit => {
            this.modelAudit = modelAudit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
