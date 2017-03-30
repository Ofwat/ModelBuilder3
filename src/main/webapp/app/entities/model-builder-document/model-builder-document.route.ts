import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ModelBuilderDocumentComponent } from './model-builder-document.component';
import { ModelBuilderDocumentDetailComponent } from './model-builder-document-detail.component';
import { ModelBuilderDocumentPopupComponent } from './model-builder-document-dialog.component';
import { ModelBuilderDocumentDeletePopupComponent } from './model-builder-document-delete-dialog.component';

import { Principal } from '../../shared';


export const modelBuilderDocumentRoute: Routes = [
  {
    path: 'model-builder-document',
    component: ModelBuilderDocumentComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelBuilderDocuments'
    }
  }, {
    path: 'model-builder-document/:id',
    component: ModelBuilderDocumentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelBuilderDocuments'
    }
  }
];

export const modelBuilderDocumentPopupRoute: Routes = [
  {
    path: 'model-builder-document-new',
    component: ModelBuilderDocumentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelBuilderDocuments'
    },
    outlet: 'popup'
  },
  {
    path: 'model-builder-document/:id/edit',
    component: ModelBuilderDocumentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelBuilderDocuments'
    },
    outlet: 'popup'
  },
  {
    path: 'model-builder-document/:id/delete',
    component: ModelBuilderDocumentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ModelBuilderDocuments'
    },
    outlet: 'popup'
  }
];
