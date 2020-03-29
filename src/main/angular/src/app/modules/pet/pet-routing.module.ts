import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PetMainComponent} from './components/pet-main/pet-main.component';

const routes: Routes = [
  {
    path: '',
    component: PetMainComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PetRoutingModule { }