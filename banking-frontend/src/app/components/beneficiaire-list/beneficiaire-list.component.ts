import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficiaireService } from '../../services/beneficiaire.service';
import { Beneficiaire } from '../../models/beneficiaire.model';

@Component({
  selector: 'app-beneficiaire-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficiaire-list.component.html',
  styleUrls: ['./beneficiaire-list.component.css']
})
export class BeneficiaireListComponent implements OnInit {
  beneficiaires: Beneficiaire[] = [];
  searchTerm: string = '';
  showForm: boolean = false;
  editMode: boolean = false;
  
  currentBeneficiaire: Beneficiaire = {
    nom: '',
    prenom: '',
    rib: '',
    type: 'PHYSIQUE'
  };

  constructor(private beneficiaireService: BeneficiaireService) {}

  ngOnInit() {
    this.loadBeneficiaires();
  }

  loadBeneficiaires() {
    this.beneficiaireService.getBeneficiaires().subscribe({
      next: (data) => this.beneficiaires = data,
      error: (err) => console.error('Error loading beneficiaires', err)
    });
  }

  search() {
    if (this.searchTerm) {
      this.beneficiaireService.searchBeneficiaires(this.searchTerm).subscribe({
        next: (data) => this.beneficiaires = data,
        error: (err) => console.error('Error searching', err)
      });
    } else {
      this.loadBeneficiaires();
    }
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.currentBeneficiaire = {
      nom: '',
      prenom: '',
      rib: '',
      type: 'PHYSIQUE'
    };
  }

  editBeneficiaire(beneficiaire: Beneficiaire) {
    this.showForm = true;
    this.editMode = true;
    this.currentBeneficiaire = { ...beneficiaire };
  }

  saveBeneficiaire() {
    if (this.editMode && this.currentBeneficiaire.id) {
      this.beneficiaireService.updateBeneficiaire(this.currentBeneficiaire.id, this.currentBeneficiaire).subscribe({
        next: () => {
          this.loadBeneficiaires();
          this.closeForm();
        },
        error: (err) => console.error('Error updating beneficiaire', err)
      });
    } else {
      this.beneficiaireService.createBeneficiaire(this.currentBeneficiaire).subscribe({
        next: () => {
          this.loadBeneficiaires();
          this.closeForm();
        },
        error: (err) => console.error('Error creating beneficiaire', err)
      });
    }
  }

  deleteBeneficiaire(id: number | undefined) {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce bénéficiaire?')) {
      this.beneficiaireService.deleteBeneficiaire(id).subscribe({
        next: () => this.loadBeneficiaires(),
        error: (err) => console.error('Error deleting beneficiaire', err)
      });
    }
  }

  closeForm() {
    this.showForm = false;
  }
}
