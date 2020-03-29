import { Component, OnInit } from '@angular/core';
import {PetModel} from '../../models/pet.model';
import {PetService} from '../../services/pet.service';
import {PersonModel} from '../../../people/models/person.model';
import {map, mergeMap} from 'rxjs/operators';
import { PeopleService } from 'src/app/modules/people/services/people.service';


@Component({
  selector: 'app-pet-main',
  templateUrl: './pet-main.component.html',
  styleUrls: ['./pet-main.component.scss']
})
export class PetMainComponent implements OnInit {
  public activePet: PetModel;
  public pet: PetModel[];
  public people: PersonModel[];

  public constructor(
    private readonly petService: PetService,
    private readonly peopleSevice: PeopleService
  ) {
    this.pet = [];
    this.clearActivePet();
  }

  public ngOnInit(): void {
    //console.log("aqui estamos");
    this.petService.list()
    .subscribe(pet => this.pet = pet);
    this.peopleSevice.list()
    .subscribe(people => this.people = people)
  }

  public onEdit(pet: PetModel): void {
    this.activePet = pet;
  }

  public onCleanForm(): void {
    this.clearActivePet();
  }

  private clearActivePet(): void {
    this.activePet = { id: undefined, name: '', owner: undefined};
  }

  public onModifyForm(pet: PetModel): void {
    if (pet.id === undefined) {
      console.log(pet);
      this.petService.create(pet)
        .pipe(
          mergeMap(() => this.petService.list())
        )
        .subscribe(pet => {
          this.pet = pet;
          this.clearActivePet();
        });
    } else {
      this.petService.modify(pet)
        .pipe(
          mergeMap(() => this.petService.list())
        )
        .subscribe(pet => {
          this.pet = pet;
          this.clearActivePet();
        });
    }
  }

  public onDelete(pet: PetModel): void {
    if (confirm(`¿Estás seguro de que deseas eliminar a ${pet.name}?`)) {
      this.petService.delete(pet)
        .pipe(
          mergeMap(() => this.petService.list())
        )
        .subscribe(pet => this.pet = pet);
    }
  }
}
