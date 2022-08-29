import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AgenciesComponent} from './agencies/agencies.component';
import {AgenciesService} from "./agencies/agencies.service";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {CreateAgencyFormComponent} from './create-agency-form/create-agency-form.component';

@NgModule({
  declarations: [
    AppComponent,
    AgenciesComponent,
    CreateAgencyFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [AgenciesService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
