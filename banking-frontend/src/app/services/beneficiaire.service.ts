import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficiaire } from '../models/beneficiaire.model';

@Injectable({
  providedIn: 'root'
})
export class BeneficiaireService {
  private apiUrl = '/api/beneficiaires';

  constructor(private http: HttpClient) { }

  getBeneficiaires(): Observable<Beneficiaire[]> {
    return this.http.get<Beneficiaire[]>(this.apiUrl);
  }

  getBeneficiaire(id: number): Observable<Beneficiaire> {
    return this.http.get<Beneficiaire>(`${this.apiUrl}/${id}`);
  }

  createBeneficiaire(beneficiaire: Beneficiaire): Observable<Beneficiaire> {
    return this.http.post<Beneficiaire>(this.apiUrl, beneficiaire);
  }

  updateBeneficiaire(id: number, beneficiaire: Beneficiaire): Observable<Beneficiaire> {
    return this.http.put<Beneficiaire>(`${this.apiUrl}/${id}`, beneficiaire);
  }

  deleteBeneficiaire(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchBeneficiaires(nom: string): Observable<Beneficiaire[]> {
    return this.http.get<Beneficiaire[]>(`${this.apiUrl}/search?nom=${nom}`);
  }
}
