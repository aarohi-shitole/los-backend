import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVouchers, NewVouchers } from '../vouchers.model';

export type PartialUpdateVouchers = Partial<IVouchers> & Pick<IVouchers, 'id'>;

type RestOf<T extends IVouchers | NewVouchers> = Omit<T, 'voucherDate' | 'lastModified'> & {
  voucherDate?: string | null;
  lastModified?: string | null;
};

export type RestVouchers = RestOf<IVouchers>;

export type NewRestVouchers = RestOf<NewVouchers>;

export type PartialUpdateRestVouchers = RestOf<PartialUpdateVouchers>;

export type EntityResponseType = HttpResponse<IVouchers>;
export type EntityArrayResponseType = HttpResponse<IVouchers[]>;

@Injectable({ providedIn: 'root' })
export class VouchersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vouchers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(vouchers: NewVouchers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchers);
    return this.http
      .post<RestVouchers>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(vouchers: IVouchers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchers);
    return this.http
      .put<RestVouchers>(`${this.resourceUrl}/${this.getVouchersIdentifier(vouchers)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(vouchers: PartialUpdateVouchers): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vouchers);
    return this.http
      .patch<RestVouchers>(`${this.resourceUrl}/${this.getVouchersIdentifier(vouchers)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVouchers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVouchers[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVouchersIdentifier(vouchers: Pick<IVouchers, 'id'>): number {
    return vouchers.id;
  }

  compareVouchers(o1: Pick<IVouchers, 'id'> | null, o2: Pick<IVouchers, 'id'> | null): boolean {
    return o1 && o2 ? this.getVouchersIdentifier(o1) === this.getVouchersIdentifier(o2) : o1 === o2;
  }

  addVouchersToCollectionIfMissing<Type extends Pick<IVouchers, 'id'>>(
    vouchersCollection: Type[],
    ...vouchersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const vouchers: Type[] = vouchersToCheck.filter(isPresent);
    if (vouchers.length > 0) {
      const vouchersCollectionIdentifiers = vouchersCollection.map(vouchersItem => this.getVouchersIdentifier(vouchersItem)!);
      const vouchersToAdd = vouchers.filter(vouchersItem => {
        const vouchersIdentifier = this.getVouchersIdentifier(vouchersItem);
        if (vouchersCollectionIdentifiers.includes(vouchersIdentifier)) {
          return false;
        }
        vouchersCollectionIdentifiers.push(vouchersIdentifier);
        return true;
      });
      return [...vouchersToAdd, ...vouchersCollection];
    }
    return vouchersCollection;
  }

  protected convertDateFromClient<T extends IVouchers | NewVouchers | PartialUpdateVouchers>(vouchers: T): RestOf<T> {
    return {
      ...vouchers,
      voucherDate: vouchers.voucherDate?.toJSON() ?? null,
      lastModified: vouchers.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVouchers: RestVouchers): IVouchers {
    return {
      ...restVouchers,
      voucherDate: restVouchers.voucherDate ? dayjs(restVouchers.voucherDate) : undefined,
      lastModified: restVouchers.lastModified ? dayjs(restVouchers.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVouchers>): HttpResponse<IVouchers> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVouchers[]>): HttpResponse<IVouchers[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
