import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PetModel} from '../../models/pet.model';
import {PetService} from '../../services/pet.service';
import {PersonModel} from '../../../people/models/person.model';
import {PeopleService} from '../../../people/services/people.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrls: ['./pet-list.component.scss']
})
export class PetListComponent {

  @Input()
  public pet: PetModel[] = [];

  @Output()
  public readonly edit: EventEmitter<PetModel>;
  @Output()
  public readonly delete: EventEmitter<PetModel>;

  public constructor() {
    this.edit = new EventEmitter<PetModel>();
    this.delete = new EventEmitter<PetModel>();
  }

  public onEdit(pet: PetModel) {
    this.edit.emit(pet);
  }

  public onDelete(pet: PetModel) {
    this.delete.emit(pet);
  }
}