import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEpay, NewEpay } from '../epay.model';

export type PartialUpdateEpay = Partial<IEpay> & Pick<IEpay, 'id'>;

type RestOf<T extends IEpay | NewEpay> = Omit<T, 'dtFromDate' | 'dtToDate' | 'lastModified'> & {
  dtFromDate?: string | null;
  dtToDate?: string | null;
  lastModified?: string | null;
};

export type RestEpay = RestOf<IEpay>;

export type NewRestEpay = RestOf<NewEpay>;

export type PartialUpdateRestEpay = RestOf<PartialUpdateEpay>;

export type EntityResponseType = HttpResponse<IEpay>;
export type EntityArrayResponseType = HttpResponse<IEpay[]>;

@Injectable({ providedIn: 'root' })
export class EpayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/epays');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(epay: NewEpay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(epay);
    return this.http.post<RestEpay>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(epay: IEpay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(epay);
    return this.http
      .put<RestEpay>(`${this.resourceUrl}/${this.getEpayIdentifier(epay)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(epay: PartialUpdateEpay): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(epay);
    return this.http
      .patch<RestEpay>(`${this.resourceUrl}/${this.getEpayIdentifier(epay)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEpay>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEpay[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEpayIdentifier(epay: Pick<IEpay, 'id'>): number {
    return epay.id;
  }

  compareEpay(o1: Pick<IEpay, 'id'> | null, o2: Pick<IEpay, 'id'> | null): boolean {
    return o1 && o2 ? this.getEpayIdentifier(o1) === this.getEpayIdentifier(o2) : o1 === o2;
  }

  addEpayToCollectionIfMissing<Type extends Pick<IEpay, 'id'>>(
    epayCollection: Type[],
    ...epaysToCheck: (Type | null | undefined)[]
  ): Type[] {
    const epays: Type[] = epaysToCheck.filter(isPresent);
    if (epays.length > 0) {
      const epayCollectionIdentifiers = epayCollection.map(epayItem => this.getEpayIdentifier(epayItem)!);
      const epaysToAdd = epays.filter(epayItem => {
        const epayIdentifier = this.getEpayIdentifier(epayItem);
        if (epayCollectionIdentifiers.includes(epayIdentifier)) {
          return false;
        }
        epayCollectionIdentifiers.push(epayIdentifier);
        return true;
      });
      return [...epaysToAdd, ...epayCollection];
    }
    return epayCollection;
  }

  protected convertDateFromClient<T extends IEpay | NewEpay | PartialUpdateEpay>(epay: T): RestOf<T> {
    return {
      ...epay,
      dtFromDate: epay.dtFromDate?.format(DATE_FORMAT) ?? null,
      dtToDate: epay.dtToDate?.format(DATE_FORMAT) ?? null,
      lastModified: epay.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEpay: RestEpay): IEpay {
    return {
      ...restEpay,
      dtFromDate: restEpay.dtFromDate ? dayjs(restEpay.dtFromDate) : undefined,
      dtToDate: restEpay.dtToDate ? dayjs(restEpay.dtToDate) : undefined,
      lastModified: restEpay.lastModified ? dayjs(restEpay.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEpay>): HttpResponse<IEpay> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEpay[]>): HttpResponse<IEpay[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
