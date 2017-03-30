import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TextBlock } from './text-block.model';
import { TextBlockService } from './text-block.service';

@Component({
    selector: 'jhi-text-block-detail',
    templateUrl: './text-block-detail.component.html'
})
export class TextBlockDetailComponent implements OnInit, OnDestroy {

    textBlock: TextBlock;
    private subscription: any;

    constructor(
        private textBlockService: TextBlockService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.textBlockService.find(id).subscribe(textBlock => {
            this.textBlock = textBlock;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
