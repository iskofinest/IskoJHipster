import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Supplier } from './supplier.model';
import { SupplierService } from './supplier.service';

@Component({
    selector: 'jhi-supplier-detail',
    templateUrl: './supplier-detail.component.html'
})
export class SupplierDetailComponent implements OnInit, OnDestroy {

    supplier: Supplier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private supplierService: SupplierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSuppliers();
    }

    load(id) {
        this.supplierService.find(id)
            .subscribe((supplierResponse: HttpResponse<Supplier>) => {
                this.supplier = supplierResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSuppliers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'supplierListModification',
            (response) => this.load(this.supplier.id)
        );
    }
}
