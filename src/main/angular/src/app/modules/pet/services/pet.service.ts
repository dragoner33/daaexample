import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {PetModel} from '../models/pet.model';

@Injectable({
  providedIn: 'root'
})
export class PetService {

  public constructor(private readonly http: HttpClient) { }

  public list(/*idpersona: number*/): Observable<PetModel[]> {
    return this.http.get<PetModel[]>(`${environment.restApi}/pet`);// esto despues de pet /${idpersona}
  }

  public create(pet: PetModel): Observable<PetModel> {
    //console.log(pet);
    const data = new HttpParams()
      .set('name', pet.name)
      .set('owner',pet.owner);

    return this.http.post<PetModel>(`${environment.restApi}/pet`, data);
  }

  public modify(pet: PetModel): Observable<PetModel> {
    //console.log(pet);
    const data = new HttpParams()
      .set('name', pet.name)
      .set('owner',pet.owner);

    return this.http.put<PetModel>(`${environment.restApi}/pet/${pet.id}`, data);
  }

  public delete(pet: PetModel): Observable<number> {
    return this.http.delete<number>(`${environment.restApi}/pet/${pet.id}`);
  }
}