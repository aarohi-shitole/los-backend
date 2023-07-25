import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherDetails, NewVoucherDetails } from '../voucher-details.model';

export type PartialUpdateVoucherDetails = Partial<IVoucherDetails> & Pick<IVoucherDetails, 'id'>;

type RestOf<T extends IVoucherDetails | NewVoucherDetails> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestVoucherDetails = RestOf<IVoucherDetails>;

export type NewRestVoucherDetails = RestOf<NewVoucherDetails>;

export type PartialUpdateRestVoucherDetails = RestOf<PartialUpdateVoucherDetails>;

export type EntityResponseType = HttpResponse<IVoucherDetails>;
export type EntityArrayResponseType = HttpResponse<IVoucherDetails[]>;

@Injectable({ providedIn: 'root' })
export class VoucherDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(voucherDetails: NewVoucherDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherDetails);
    return this.http
      .post<RestVoucherDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(voucherDetails: IVoucherDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherDetails);
    return this.http
      .put<RestVoucherDetails>(`${this.resourceUrl}/${this.getVoucherDetailsIdentifier(voucherDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(voucherDetails: PartialUpdateVoucherDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voucherDetails);
    return this.http
      .patch<RestVoucherDetails>(`${this.resourceUrl}/${this.getVoucherDetailsIdentifier(voucherDetails)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestVoucherDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestVoucherDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVoucherDetailsIdentifier(voucherDetails: Pick<IVoucherDetails, 'id'>): number {
    return voucherDetails.id;
  }

  compareVoucherDetails(o1: Pick<IVoucherDetails, 'id'> | null, o2: Pick<IVoucherDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getVoucherDetailsIdentifier(o1) === this.getVoucherDetailsIdentifier(o2) : o1 === o2;
  }

  addVoucherDetailsToCollectionIfMissing<Type extends Pick<IVoucherDetails, 'id'>>(
    voucherDetailsCollection: Type[],
    ...voucherDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const voucherDetails: Type[] = voucherDetailsToCheck.filter(isPresent);
    if (voucherDetails.length > 0) {
      const voucherDetailsCollectionIdentifiers = voucherDetailsCollection.map(
        voucherDetailsItem => this.getVoucherDetailsIdentifier(voucherDetailsItem)!
      );
      const voucherDetailsToAdd = voucherDetails.filter(voucherDetailsItem => {
        const voucherDetailsIdentifier = this.getVoucherDetailsIdentifier(voucherDetailsItem);
        if (voucherDetailsCollectionIdentifiers.includes(voucherDetailsIdentifier)) {
          return false;
        }
        voucherDetailsCollectionIdentifiers.push(voucherDetailsIdentifier);
        return true;
      });
      return [...voucherDetailsToAdd, ...voucherDetailsCollection];
    }
    return voucherDetailsCollection;
  }

  protected convertDateFromClient<T extends IVoucherDetails | NewVoucherDetails | PartialUpdateVoucherDetails>(
    voucherDetails: T
  ): RestOf<T> {
    return {
      ...voucherDetails,
      lastModified: voucherDetails.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restVoucherDetails: RestVoucherDetails): IVoucherDetails {
    return {
      ...restVoucherDetails,
      lastModified: restVoucherDetails.lastModified ? dayjs(restVoucherDetails.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestVoucherDetails>): HttpResponse<IVoucherDetails> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestVoucherDetails[]>): HttpResponse<IVoucherDetails[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
