import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModelBuilderDocument } from './model-builder-document.model';
import { ModelBuilderDocumentService } from './model-builder-document.service';

@Component({
    selector: 'jhi-model-builder-document-detail',
    templateUrl: './model-builder-document-detail.component.html'
})
export class ModelBuilderDocumentDetailComponent implements OnInit, OnDestroy {

    modelBuilderDocument: ModelBuilderDocument;
    private subscription: any;

    constructor(
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.modelBuilderDocumentService.find(id).subscribe(modelBuilderDocument => {
            this.modelBuilderDocument = modelBuilderDocument;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
