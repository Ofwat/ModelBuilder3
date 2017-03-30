import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AuditDetailsComponent } from './audit-details.component';
import { AuditDetailsDetailComponent } from './audit-details-detail.component';
import { AuditDetailsPopupComponent } from './audit-details-dialog.component';
import { AuditDetailsDeletePopupComponent } from './audit-details-delete-dialog.component';

import { Principal } from '../../shared';


export const auditDetailsRoute: Routes = [
  {
    path: 'audit-details',
    component: AuditDetailsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditDetails'
    }
  }, {
    path: 'audit-details/:id',
    component: AuditDetailsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditDetails'
    }
  }
];

export const auditDetailsPopupRoute: Routes = [
  {
    path: 'audit-details-new',
    component: AuditDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'audit-details/:id/edit',
    component: AuditDetailsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditDetails'
    },
    outlet: 'popup'
  },
  {
    path: 'audit-details/:id/delete',
    component: AuditDetailsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AuditDetails'
    },
    outlet: 'popup'
  }
];
