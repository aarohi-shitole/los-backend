import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGuarantor, NewGuarantor } from '../guarantor.model';

export type PartialUpdateGuarantor = Partial<IGuarantor> & Pick<IGuarantor, 'id'>;

type RestOf<T extends IGuarantor | NewGuarantor> = Omit<T, 'dob' | 'lastModified' | 'createdOn'> & {
  dob?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestGuarantor = RestOf<IGuarantor>;

export type NewRestGuarantor = RestOf<NewGuarantor>;

export type PartialUpdateRestGuarantor = RestOf<PartialUpdateGuarantor>;

export type EntityResponseType = HttpResponse<IGuarantor>;
export type EntityArrayResponseType = HttpResponse<IGuarantor[]>;

@Injectable({ providedIn: 'root' })
export class GuarantorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/guarantors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(guarantor: NewGuarantor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guarantor);
    return this.http
      .post<RestGuarantor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(guarantor: IGuarantor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guarantor);
    return this.http
      .put<RestGuarantor>(`${this.resourceUrl}/${this.getGuarantorIdentifier(guarantor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(guarantor: PartialUpdateGuarantor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(guarantor);
    return this.http
      .patch<RestGuarantor>(`${this.resourceUrl}/${this.getGuarantorIdentifier(guarantor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGuarantor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGuarantor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGuarantorIdentifier(guarantor: Pick<IGuarantor, 'id'>): number {
    return guarantor.id;
  }

  compareGuarantor(o1: Pick<IGuarantor, 'id'> | null, o2: Pick<IGuarantor, 'id'> | null): boolean {
    return o1 && o2 ? this.getGuarantorIdentifier(o1) === this.getGuarantorIdentifier(o2) : o1 === o2;
  }

  addGuarantorToCollectionIfMissing<Type extends Pick<IGuarantor, 'id'>>(
    guarantorCollection: Type[],
    ...guarantorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const guarantors: Type[] = guarantorsToCheck.filter(isPresent);
    if (guarantors.length > 0) {
      const guarantorCollectionIdentifiers = guarantorCollection.map(guarantorItem => this.getGuarantorIdentifier(guarantorItem)!);
      const guarantorsToAdd = guarantors.filter(guarantorItem => {
        const guarantorIdentifier = this.getGuarantorIdentifier(guarantorItem);
        if (guarantorCollectionIdentifiers.includes(guarantorIdentifier)) {
          return false;
        }
        guarantorCollectionIdentifiers.push(guarantorIdentifier);
        return true;
      });
      return [...guarantorsToAdd, ...guarantorCollection];
    }
    return guarantorCollection;
  }

  protected convertDateFromClient<T extends IGuarantor | NewGuarantor | PartialUpdateGuarantor>(guarantor: T): RestOf<T> {
    return {
      ...guarantor,
      dob: guarantor.dob?.format(DATE_FORMAT) ?? null,
      lastModified: guarantor.lastModified?.toJSON() ?? null,
      createdOn: guarantor.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGuarantor: RestGuarantor): IGuarantor {
    return {
      ...restGuarantor,
      dob: restGuarantor.dob ? dayjs(restGuarantor.dob) : undefined,
      lastModified: restGuarantor.lastModified ? dayjs(restGuarantor.lastModified) : undefined,
      createdOn: restGuarantor.createdOn ? dayjs(restGuarantor.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGuarantor>): HttpResponse<IGuarantor> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGuarantor[]>): HttpResponse<IGuarantor[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
