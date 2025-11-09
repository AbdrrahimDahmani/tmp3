export interface Beneficiaire {
  id?: number;
  nom: string;
  prenom: string;
  rib: string;
  type: 'PHYSIQUE' | 'MORALE';
}
