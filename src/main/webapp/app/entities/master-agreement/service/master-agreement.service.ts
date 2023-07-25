import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMasterAgreement, NewMasterAgreement } from '../master-agreement.model';

export type PartialUpdateMasterAgreement = Partial<IMasterAgreement> & Pick<IMasterAgreement, 'id'>;

type RestOf<T extends IMasterAgreement | NewMasterAgreement> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestMasterAgreement = RestOf<IMasterAgreement>;

export type NewRestMasterAgreement = RestOf<NewMasterAgreement>;

export type PartialUpdateRestMasterAgreement = RestOf<PartialUpdateMasterAgreement>;

export type EntityResponseType = HttpResponse<IMasterAgreement>;
export type EntityArrayResponseType = HttpResponse<IMasterAgreement[]>;

@Injectable({ providedIn: 'root' })
export class MasterAgreementService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/master-agreements');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(masterAgreement: NewMasterAgreement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(masterAgreement);
    return this.http
      .post<RestMasterAgreement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(masterAgreement: IMasterAgreement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(masterAgreement);
    return this.http
      .put<RestMasterAgreement>(`${this.resourceUrl}/${this.getMasterAgreementIdentifier(masterAgreement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(masterAgreement: PartialUpdateMasterAgreement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(masterAgreement);
    return this.http
      .patch<RestMasterAgreement>(`${this.resourceUrl}/${this.getMasterAgreementIdentifier(masterAgreement)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMasterAgreement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMasterAgreement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMasterAgreementIdentifier(masterAgreement: Pick<IMasterAgreement, 'id'>): number {
    return masterAgreement.id;
  }

  compareMasterAgreement(o1: Pick<IMasterAgreement, 'id'> | null, o2: Pick<IMasterAgreement, 'id'> | null): boolean {
    return o1 && o2 ? this.getMasterAgreementIdentifier(o1) === this.getMasterAgreementIdentifier(o2) : o1 === o2;
  }

  addMasterAgreementToCollectionIfMissing<Type extends Pick<IMasterAgreement, 'id'>>(
    masterAgreementCollection: Type[],
    ...masterAgreementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const masterAgreements: Type[] = masterAgreementsToCheck.filter(isPresent);
    if (masterAgreements.length > 0) {
      const masterAgreementCollectionIdentifiers = masterAgreementCollection.map(
        masterAgreementItem => this.getMasterAgreementIdentifier(masterAgreementItem)!
      );
      const masterAgreementsToAdd = masterAgreements.filter(masterAgreementItem => {
        const masterAgreementIdentifier = this.getMasterAgreementIdentifier(masterAgreementItem);
        if (masterAgreementCollectionIdentifiers.includes(masterAgreementIdentifier)) {
          return false;
        }
        masterAgreementCollectionIdentifiers.push(masterAgreementIdentifier);
        return true;
      });
      return [...masterAgreementsToAdd, ...masterAgreementCollection];
    }
    return masterAgreementCollection;
  }

  protected convertDateFromClient<T extends IMasterAgreement | NewMasterAgreement | PartialUpdateMasterAgreement>(
    masterAgreement: T
  ): RestOf<T> {
    return {
      ...masterAgreement,
      lastModified: masterAgreement.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMasterAgreement: RestMasterAgreement): IMasterAgreement {
    return {
      ...restMasterAgreement,
      lastModified: restMasterAgreement.lastModified ? dayjs(restMasterAgreement.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMasterAgreement>): HttpResponse<IMasterAgreement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMasterAgreement[]>): HttpResponse<IMasterAgreement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
