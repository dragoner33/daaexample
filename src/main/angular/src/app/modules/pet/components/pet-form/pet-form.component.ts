import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PetModel} from '../../models/pet.model';
import { PersonModel } from 'src/app/modules/people/models/person.model';

@Component({
  selector: 'app-pet-form',
  templateUrl: './pet-form.component.html',
  styleUrls: ['./pet-form.component.scss']
})
export class PetFormComponent {
  public activePet: PetModel;

  @Output()
  public readonly modify: EventEmitter<PetModel>;
  @Output()
  public readonly clean: EventEmitter<never>;

  public name: string;
  public owner: string;

  public constructor() {
    this.modify = new EventEmitter<PetModel>();
    this.clean = new EventEmitter<never>();
  }
  @Input()
  public people: PersonModel[] = [];

  @Input()
  public set pet(pet: PetModel) {
    this.activePet = pet;
    this.name = pet.name;
    this.owner = pet.owner;
  }

  public get pet(): PetModel {
    return this.activePet;
  }

  public onModify() {
    this.modify.emit({
      id: this.pet.id,
      name: this.name,
      owner: this.owner
    });
  }

  public onClean() {
    this.clean.emit();
  }

}