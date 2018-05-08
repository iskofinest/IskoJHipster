import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Supplier } from './supplier.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Supplier>;

@Injectable()
export class SupplierService {

    private resourceUrl =  SERVER_API_URL + 'api/suppliers';

    constructor(private http: HttpClient) { }

    create(supplier: Supplier): Observable<EntityResponseType> {
        const copy = this.convert(supplier);
        return this.http.post<Supplier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(supplier: Supplier): Observable<EntityResponseType> {
        const copy = this.convert(supplier);
        return this.http.put<Supplier>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Supplier>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Supplier[]>> {
        const options = createRequestOption(req);
        return this.http.get<Supplier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Supplier[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Supplier = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Supplier[]>): HttpResponse<Supplier[]> {
        const jsonResponse: Supplier[] = res.body;
        const body: Supplier[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Supplier.
     */
    private convertItemFromServer(supplier: Supplier): Supplier {
        const copy: Supplier = Object.assign({}, supplier);
        return copy;
    }

    /**
     * Convert a Supplier to a JSON which can be sent to the server.
     */
    private convert(supplier: Supplier): Supplier {
        const copy: Supplier = Object.assign({}, supplier);
        return copy;
    }
}
