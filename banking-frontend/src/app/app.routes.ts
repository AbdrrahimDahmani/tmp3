import { Routes } from '@angular/router';
import { BeneficiaireListComponent } from './components/beneficiaire-list/beneficiaire-list.component';
import { VirementListComponent } from './components/virement-list/virement-list.component';

export const routes: Routes = [
  { path: '', redirectTo: '/beneficiaires', pathMatch: 'full' },
  { path: 'beneficiaires', component: BeneficiaireListComponent },
  { path: 'virements', component: VirementListComponent }
];
