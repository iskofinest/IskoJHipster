import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SupplierComponent } from './supplier.component';
import { SupplierDetailComponent } from './supplier-detail.component';
import { SupplierPopupComponent } from './supplier-dialog.component';
import { SupplierDeletePopupComponent } from './supplier-delete-dialog.component';

@Injectable()
export class SupplierResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const supplierRoute: Routes = [
    {
        path: 'supplier',
        component: SupplierComponent,
        resolve: {
            'pagingParams': SupplierResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'supplier/:id',
        component: SupplierDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplierPopupRoute: Routes = [
    {
        path: 'supplier-new',
        component: SupplierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'supplier/:id/edit',
        component: SupplierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'supplier/:id/delete',
        component: SupplierDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Suppliers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
