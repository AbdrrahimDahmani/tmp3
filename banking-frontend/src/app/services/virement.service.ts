import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Virement } from '../models/virement.model';

@Injectable({
  providedIn: 'root'
})
export class VirementService {
  private apiUrl = '/api/virements';

  constructor(private http: HttpClient) { }

  getVirements(): Observable<Virement[]> {
    return this.http.get<Virement[]>(this.apiUrl);
  }

  getVirement(id: number): Observable<Virement> {
    return this.http.get<Virement>(`${this.apiUrl}/${id}`);
  }

  createVirement(virement: Virement): Observable<Virement> {
    return this.http.post<Virement>(this.apiUrl, virement);
  }

  updateVirement(id: number, virement: Virement): Observable<Virement> {
    return this.http.put<Virement>(`${this.apiUrl}/${id}`, virement);
  }

  deleteVirement(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getVirementsByBeneficiaire(beneficiaireId: number): Observable<Virement[]> {
    return this.http.get<Virement[]>(`${this.apiUrl}/beneficiaire/${beneficiaireId}`);
  }
}
