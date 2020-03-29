/*
 * DAA Example
 *
 * Copyright (C) 2019 - Miguel Reboiro-Jato.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PersonModel} from '../../models/person.model';
import {Router, RouterLink} from '@angular/router';
import {PeopleService} from '../../services/people.service';
import {PetService} from '../../../pet/services/pet.service';
import { PetModel } from 'src/app/modules/pet/models/pet.model';

@Component({
  selector: 'app-people-list',
  templateUrl: './people-list.component.html',
  styleUrls: ['./people-list.component.scss']
})
export class PeopleListComponent {

  @Input()
  public people: PersonModel[] = [];

  @Output()
  public readonly edit: EventEmitter<PersonModel>;
  @Output()
  public readonly delete: EventEmitter<PersonModel>;

  public pet: PetModel[];

  public constructor(private readonly petService: PetService,  private readonly router: Router) {
    
    this.edit = new EventEmitter<PersonModel>();
    this.delete = new EventEmitter<PersonModel>();
    //this.onPet = new 
  }

  public onEdit(person: PersonModel) {
    this.edit.emit(person);
  }

  public onDelete(person: PersonModel) {
    this.delete.emit(person);
  }

  public onPet(person: PersonModel) {
    this.router.navigate(['/pet']);
    console.log(person.id);
  }
}
