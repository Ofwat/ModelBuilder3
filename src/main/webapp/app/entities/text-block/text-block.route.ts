import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TextBlockComponent } from './text-block.component';
import { TextBlockDetailComponent } from './text-block-detail.component';
import { TextBlockPopupComponent } from './text-block-dialog.component';
import { TextBlockDeletePopupComponent } from './text-block-delete-dialog.component';

import { Principal } from '../../shared';


export const textBlockRoute: Routes = [
  {
    path: 'text-block',
    component: TextBlockComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TextBlocks'
    }
  }, {
    path: 'text-block/:id',
    component: TextBlockDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TextBlocks'
    }
  }
];

export const textBlockPopupRoute: Routes = [
  {
    path: 'text-block-new',
    component: TextBlockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TextBlocks'
    },
    outlet: 'popup'
  },
  {
    path: 'text-block/:id/edit',
    component: TextBlockPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TextBlocks'
    },
    outlet: 'popup'
  },
  {
    path: 'text-block/:id/delete',
    component: TextBlockDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'TextBlocks'
    },
    outlet: 'popup'
  }
];
