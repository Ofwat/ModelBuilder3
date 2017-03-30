import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuditDetails } from './audit-details.model';
import { AuditDetailsService } from './audit-details.service';

@Component({
    selector: 'jhi-audit-details-detail',
    templateUrl: './audit-details-detail.component.html'
})
export class AuditDetailsDetailComponent implements OnInit, OnDestroy {

    auditDetails: AuditDetails;
    private subscription: any;

    constructor(
        private auditDetailsService: AuditDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.auditDetailsService.find(id).subscribe(auditDetails => {
            this.auditDetails = auditDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
