import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VirementService } from '../../services/virement.service';
import { BeneficiaireService } from '../../services/beneficiaire.service';
import { Virement } from '../../models/virement.model';
import { Beneficiaire } from '../../models/beneficiaire.model';

@Component({
  selector: 'app-virement-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './virement-list.component.html',
  styleUrls: ['./virement-list.component.css']
})
export class VirementListComponent implements OnInit {
  virements: Virement[] = [];
  beneficiaires: Beneficiaire[] = [];
  showForm: boolean = false;
  editMode: boolean = false;
  
  currentVirement: Virement = {
    beneficiaireId: 0,
    ribSource: '',
    montant: 0,
    description: '',
    dateVirement: new Date(),
    type: 'NORMAL'
  };

  constructor(
    private virementService: VirementService,
    private beneficiaireService: BeneficiaireService
  ) {}

  ngOnInit() {
    this.loadVirements();
    this.loadBeneficiaires();
  }

  loadVirements() {
    this.virementService.getVirements().subscribe({
      next: (data) => this.virements = data,
      error: (err) => console.error('Error loading virements', err)
    });
  }

  loadBeneficiaires() {
    this.beneficiaireService.getBeneficiaires().subscribe({
      next: (data) => this.beneficiaires = data,
      error: (err) => console.error('Error loading beneficiaires', err)
    });
  }

  openForm() {
    this.showForm = true;
    this.editMode = false;
    this.currentVirement = {
      beneficiaireId: this.beneficiaires.length > 0 ? this.beneficiaires[0].id || 0 : 0,
      ribSource: '',
      montant: 0,
      description: '',
      dateVirement: new Date(),
      type: 'NORMAL'
    };
  }

  editVirement(virement: Virement) {
    this.showForm = true;
    this.editMode = true;
    this.currentVirement = { ...virement };
  }

  saveVirement() {
    if (this.editMode && this.currentVirement.id) {
      this.virementService.updateVirement(this.currentVirement.id, this.currentVirement).subscribe({
        next: () => {
          this.loadVirements();
          this.closeForm();
        },
        error: (err) => console.error('Error updating virement', err)
      });
    } else {
      this.virementService.createVirement(this.currentVirement).subscribe({
        next: () => {
          this.loadVirements();
          this.closeForm();
        },
        error: (err) => console.error('Error creating virement', err)
      });
    }
  }

  deleteVirement(id: number | undefined) {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer ce virement?')) {
      this.virementService.deleteVirement(id).subscribe({
        next: () => this.loadVirements(),
        error: (err) => console.error('Error deleting virement', err)
      });
    }
  }

  closeForm() {
    this.showForm = false;
  }

  getBeneficiaireNom(beneficiaire: any): string {
    if (beneficiaire) {
      return `${beneficiaire.nom} ${beneficiaire.prenom}`;
    }
    return 'N/A';
  }
}
