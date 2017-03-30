import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuditChange } from './audit-change.model';
import { AuditChangeService } from './audit-change.service';

@Component({
    selector: 'jhi-audit-change-detail',
    templateUrl: './audit-change-detail.component.html'
})
export class AuditChangeDetailComponent implements OnInit, OnDestroy {

    auditChange: AuditChange;
    private subscription: any;

    constructor(
        private auditChangeService: AuditChangeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.auditChangeService.find(id).subscribe(auditChange => {
            this.auditChange = auditChange;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
