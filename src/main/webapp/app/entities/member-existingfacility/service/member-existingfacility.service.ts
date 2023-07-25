import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberExistingfacility, NewMemberExistingfacility } from '../member-existingfacility.model';

export type PartialUpdateMemberExistingfacility = Partial<IMemberExistingfacility> & Pick<IMemberExistingfacility, 'id'>;

type RestOf<T extends IMemberExistingfacility | NewMemberExistingfacility> = Omit<T, 'sectionDate' | 'lastModified' | 'createdOn'> & {
  sectionDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestMemberExistingfacility = RestOf<IMemberExistingfacility>;

export type NewRestMemberExistingfacility = RestOf<NewMemberExistingfacility>;

export type PartialUpdateRestMemberExistingfacility = RestOf<PartialUpdateMemberExistingfacility>;

export type EntityResponseType = HttpResponse<IMemberExistingfacility>;
export type EntityArrayResponseType = HttpResponse<IMemberExistingfacility[]>;

@Injectable({ providedIn: 'root' })
export class MemberExistingfacilityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-existingfacilities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberExistingfacility: NewMemberExistingfacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberExistingfacility);
    return this.http
      .post<RestMemberExistingfacility>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(memberExistingfacility: IMemberExistingfacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberExistingfacility);
    return this.http
      .put<RestMemberExistingfacility>(`${this.resourceUrl}/${this.getMemberExistingfacilityIdentifier(memberExistingfacility)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(memberExistingfacility: PartialUpdateMemberExistingfacility): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(memberExistingfacility);
    return this.http
      .patch<RestMemberExistingfacility>(`${this.resourceUrl}/${this.getMemberExistingfacilityIdentifier(memberExistingfacility)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMemberExistingfacility>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMemberExistingfacility[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberExistingfacilityIdentifier(memberExistingfacility: Pick<IMemberExistingfacility, 'id'>): number {
    return memberExistingfacility.id;
  }

  compareMemberExistingfacility(o1: Pick<IMemberExistingfacility, 'id'> | null, o2: Pick<IMemberExistingfacility, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberExistingfacilityIdentifier(o1) === this.getMemberExistingfacilityIdentifier(o2) : o1 === o2;
  }

  addMemberExistingfacilityToCollectionIfMissing<Type extends Pick<IMemberExistingfacility, 'id'>>(
    memberExistingfacilityCollection: Type[],
    ...memberExistingfacilitiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberExistingfacilities: Type[] = memberExistingfacilitiesToCheck.filter(isPresent);
    if (memberExistingfacilities.length > 0) {
      const memberExistingfacilityCollectionIdentifiers = memberExistingfacilityCollection.map(
        memberExistingfacilityItem => this.getMemberExistingfacilityIdentifier(memberExistingfacilityItem)!
      );
      const memberExistingfacilitiesToAdd = memberExistingfacilities.filter(memberExistingfacilityItem => {
        const memberExistingfacilityIdentifier = this.getMemberExistingfacilityIdentifier(memberExistingfacilityItem);
        if (memberExistingfacilityCollectionIdentifiers.includes(memberExistingfacilityIdentifier)) {
          return false;
        }
        memberExistingfacilityCollectionIdentifiers.push(memberExistingfacilityIdentifier);
        return true;
      });
      return [...memberExistingfacilitiesToAdd, ...memberExistingfacilityCollection];
    }
    return memberExistingfacilityCollection;
  }

  protected convertDateFromClient<T extends IMemberExistingfacility | NewMemberExistingfacility | PartialUpdateMemberExistingfacility>(
    memberExistingfacility: T
  ): RestOf<T> {
    return {
      ...memberExistingfacility,
      sectionDate: memberExistingfacility.sectionDate?.toJSON() ?? null,
      lastModified: memberExistingfacility.lastModified?.toJSON() ?? null,
      createdOn: memberExistingfacility.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMemberExistingfacility: RestMemberExistingfacility): IMemberExistingfacility {
    return {
      ...restMemberExistingfacility,
      sectionDate: restMemberExistingfacility.sectionDate ? dayjs(restMemberExistingfacility.sectionDate) : undefined,
      lastModified: restMemberExistingfacility.lastModified ? dayjs(restMemberExistingfacility.lastModified) : undefined,
      createdOn: restMemberExistingfacility.createdOn ? dayjs(restMemberExistingfacility.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMemberExistingfacility>): HttpResponse<IMemberExistingfacility> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMemberExistingfacility[]>): HttpResponse<IMemberExistingfacility[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
