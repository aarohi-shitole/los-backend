import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrgPrerequisiteDoc, NewOrgPrerequisiteDoc } from '../org-prerequisite-doc.model';

export type PartialUpdateOrgPrerequisiteDoc = Partial<IOrgPrerequisiteDoc> & Pick<IOrgPrerequisiteDoc, 'id'>;

type RestOf<T extends IOrgPrerequisiteDoc | NewOrgPrerequisiteDoc> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestOrgPrerequisiteDoc = RestOf<IOrgPrerequisiteDoc>;

export type NewRestOrgPrerequisiteDoc = RestOf<NewOrgPrerequisiteDoc>;

export type PartialUpdateRestOrgPrerequisiteDoc = RestOf<PartialUpdateOrgPrerequisiteDoc>;

export type EntityResponseType = HttpResponse<IOrgPrerequisiteDoc>;
export type EntityArrayResponseType = HttpResponse<IOrgPrerequisiteDoc[]>;

@Injectable({ providedIn: 'root' })
export class OrgPrerequisiteDocService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/org-prerequisite-docs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orgPrerequisiteDoc: NewOrgPrerequisiteDoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orgPrerequisiteDoc);
    return this.http
      .post<RestOrgPrerequisiteDoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(orgPrerequisiteDoc: IOrgPrerequisiteDoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orgPrerequisiteDoc);
    return this.http
      .put<RestOrgPrerequisiteDoc>(`${this.resourceUrl}/${this.getOrgPrerequisiteDocIdentifier(orgPrerequisiteDoc)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(orgPrerequisiteDoc: PartialUpdateOrgPrerequisiteDoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orgPrerequisiteDoc);
    return this.http
      .patch<RestOrgPrerequisiteDoc>(`${this.resourceUrl}/${this.getOrgPrerequisiteDocIdentifier(orgPrerequisiteDoc)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrgPrerequisiteDoc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrgPrerequisiteDoc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrgPrerequisiteDocIdentifier(orgPrerequisiteDoc: Pick<IOrgPrerequisiteDoc, 'id'>): number {
    return orgPrerequisiteDoc.id;
  }

  compareOrgPrerequisiteDoc(o1: Pick<IOrgPrerequisiteDoc, 'id'> | null, o2: Pick<IOrgPrerequisiteDoc, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrgPrerequisiteDocIdentifier(o1) === this.getOrgPrerequisiteDocIdentifier(o2) : o1 === o2;
  }

  addOrgPrerequisiteDocToCollectionIfMissing<Type extends Pick<IOrgPrerequisiteDoc, 'id'>>(
    orgPrerequisiteDocCollection: Type[],
    ...orgPrerequisiteDocsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orgPrerequisiteDocs: Type[] = orgPrerequisiteDocsToCheck.filter(isPresent);
    if (orgPrerequisiteDocs.length > 0) {
      const orgPrerequisiteDocCollectionIdentifiers = orgPrerequisiteDocCollection.map(
        orgPrerequisiteDocItem => this.getOrgPrerequisiteDocIdentifier(orgPrerequisiteDocItem)!
      );
      const orgPrerequisiteDocsToAdd = orgPrerequisiteDocs.filter(orgPrerequisiteDocItem => {
        const orgPrerequisiteDocIdentifier = this.getOrgPrerequisiteDocIdentifier(orgPrerequisiteDocItem);
        if (orgPrerequisiteDocCollectionIdentifiers.includes(orgPrerequisiteDocIdentifier)) {
          return false;
        }
        orgPrerequisiteDocCollectionIdentifiers.push(orgPrerequisiteDocIdentifier);
        return true;
      });
      return [...orgPrerequisiteDocsToAdd, ...orgPrerequisiteDocCollection];
    }
    return orgPrerequisiteDocCollection;
  }

  protected convertDateFromClient<T extends IOrgPrerequisiteDoc | NewOrgPrerequisiteDoc | PartialUpdateOrgPrerequisiteDoc>(
    orgPrerequisiteDoc: T
  ): RestOf<T> {
    return {
      ...orgPrerequisiteDoc,
      lastModified: orgPrerequisiteDoc.lastModified?.toJSON() ?? null,
      createdOn: orgPrerequisiteDoc.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOrgPrerequisiteDoc: RestOrgPrerequisiteDoc): IOrgPrerequisiteDoc {
    return {
      ...restOrgPrerequisiteDoc,
      lastModified: restOrgPrerequisiteDoc.lastModified ? dayjs(restOrgPrerequisiteDoc.lastModified) : undefined,
      createdOn: restOrgPrerequisiteDoc.createdOn ? dayjs(restOrgPrerequisiteDoc.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrgPrerequisiteDoc>): HttpResponse<IOrgPrerequisiteDoc> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrgPrerequisiteDoc[]>): HttpResponse<IOrgPrerequisiteDoc[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
