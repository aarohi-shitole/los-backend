import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISequenceGenerator, NewSequenceGenerator } from '../sequence-generator.model';

export type PartialUpdateSequenceGenerator = Partial<ISequenceGenerator> & Pick<ISequenceGenerator, 'id'>;

export type EntityResponseType = HttpResponse<ISequenceGenerator>;
export type EntityArrayResponseType = HttpResponse<ISequenceGenerator[]>;

@Injectable({ providedIn: 'root' })
export class SequenceGeneratorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sequence-generators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sequenceGenerator: NewSequenceGenerator): Observable<EntityResponseType> {
    return this.http.post<ISequenceGenerator>(this.resourceUrl, sequenceGenerator, { observe: 'response' });
  }

  update(sequenceGenerator: ISequenceGenerator): Observable<EntityResponseType> {
    return this.http.put<ISequenceGenerator>(
      `${this.resourceUrl}/${this.getSequenceGeneratorIdentifier(sequenceGenerator)}`,
      sequenceGenerator,
      { observe: 'response' }
    );
  }

  partialUpdate(sequenceGenerator: PartialUpdateSequenceGenerator): Observable<EntityResponseType> {
    return this.http.patch<ISequenceGenerator>(
      `${this.resourceUrl}/${this.getSequenceGeneratorIdentifier(sequenceGenerator)}`,
      sequenceGenerator,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISequenceGenerator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISequenceGenerator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSequenceGeneratorIdentifier(sequenceGenerator: Pick<ISequenceGenerator, 'id'>): number {
    return sequenceGenerator.id;
  }

  compareSequenceGenerator(o1: Pick<ISequenceGenerator, 'id'> | null, o2: Pick<ISequenceGenerator, 'id'> | null): boolean {
    return o1 && o2 ? this.getSequenceGeneratorIdentifier(o1) === this.getSequenceGeneratorIdentifier(o2) : o1 === o2;
  }

  addSequenceGeneratorToCollectionIfMissing<Type extends Pick<ISequenceGenerator, 'id'>>(
    sequenceGeneratorCollection: Type[],
    ...sequenceGeneratorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sequenceGenerators: Type[] = sequenceGeneratorsToCheck.filter(isPresent);
    if (sequenceGenerators.length > 0) {
      const sequenceGeneratorCollectionIdentifiers = sequenceGeneratorCollection.map(
        sequenceGeneratorItem => this.getSequenceGeneratorIdentifier(sequenceGeneratorItem)!
      );
      const sequenceGeneratorsToAdd = sequenceGenerators.filter(sequenceGeneratorItem => {
        const sequenceGeneratorIdentifier = this.getSequenceGeneratorIdentifier(sequenceGeneratorItem);
        if (sequenceGeneratorCollectionIdentifiers.includes(sequenceGeneratorIdentifier)) {
          return false;
        }
        sequenceGeneratorCollectionIdentifiers.push(sequenceGeneratorIdentifier);
        return true;
      });
      return [...sequenceGeneratorsToAdd, ...sequenceGeneratorCollection];
    }
    return sequenceGeneratorCollection;
  }
}
