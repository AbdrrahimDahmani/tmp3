import { Beneficiaire } from './beneficiaire.model';

export interface Virement {
  id?: number;
  beneficiaireId: number;
  ribSource: string;
  montant: number;
  description: string;
  dateVirement: Date | string;
  type: 'NORMAL' | 'INSTANTANE';
  beneficiaire?: Beneficiaire;
}
