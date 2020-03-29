import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PetRoutingModule} from './pet-routing.module';
import {PetListComponent} from './components/pet-list/pet-list.component';
import {PetFormComponent} from './components/pet-form/pet-form.component';
import {PetMainComponent} from './components/pet-main/pet-main.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    PetFormComponent,
    PetListComponent,
    PetMainComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    PetRoutingModule
  ]
})
export class PetModule {
}