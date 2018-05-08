/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IskoJHipsterTestModule } from '../../../test.module';
import { SupplierDetailComponent } from '../../../../../../main/webapp/app/entities/supplier/supplier-detail.component';
import { SupplierService } from '../../../../../../main/webapp/app/entities/supplier/supplier.service';
import { Supplier } from '../../../../../../main/webapp/app/entities/supplier/supplier.model';

describe('Component Tests', () => {

    describe('Supplier Management Detail Component', () => {
        let comp: SupplierDetailComponent;
        let fixture: ComponentFixture<SupplierDetailComponent>;
        let service: SupplierService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [IskoJHipsterTestModule],
                declarations: [SupplierDetailComponent],
                providers: [
                    SupplierService
                ]
            })
            .overrideTemplate(SupplierDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SupplierDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplierService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Supplier(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.supplier).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
