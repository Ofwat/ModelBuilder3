import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ModelDetails } from './model-details.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ModelDetailsService {

    private resourceUrl = 'api/model-details';
    private resourceSearchUrl = 'api/_search/model-details';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(modelDetails: ModelDetails): Observable<ModelDetails> {
        let copy: ModelDetails = Object.assign({}, modelDetails);
        copy.lastModified = this.dateUtils.toDate(modelDetails.lastModified);
        copy.created = this.dateUtils.toDate(modelDetails.created);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(modelDetails: ModelDetails): Observable<ModelDetails> {
        let copy: ModelDetails = Object.assign({}, modelDetails);

        copy.lastModified = this.dateUtils.toDate(modelDetails.lastModified);

        copy.created = this.dateUtils.toDate(modelDetails.created);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ModelDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.lastModified = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.lastModified);
            jsonResponse.created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.created);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].lastModified = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].lastModified);
            jsonResponse[i].created = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].created);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
