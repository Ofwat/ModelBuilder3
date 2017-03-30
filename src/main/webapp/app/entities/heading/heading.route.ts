import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HeadingComponent } from './heading.component';
import { HeadingDetailComponent } from './heading-detail.component';
import { HeadingPopupComponent } from './heading-dialog.component';
import { HeadingDeletePopupComponent } from './heading-delete-dialog.component';

import { Principal } from '../../shared';


export const headingRoute: Routes = [
  {
    path: 'heading',
    component: HeadingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Headings'
    }
  }, {
    path: 'heading/:id',
    component: HeadingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Headings'
    }
  }
];

export const headingPopupRoute: Routes = [
  {
    path: 'heading-new',
    component: HeadingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Headings'
    },
    outlet: 'popup'
  },
  {
    path: 'heading/:id/edit',
    component: HeadingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Headings'
    },
    outlet: 'popup'
  },
  {
    path: 'heading/:id/delete',
    component: HeadingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Headings'
    },
    outlet: 'popup'
  }
];
