import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import { switchMap } from 'rxjs/operators';
import {environment} from "../../environments/environment";

@Component({
  selector: 'agencies',
  templateUrl: './agencies.component.html'
})
export class AgenciesComponent implements OnInit {

  title = "List of agencies"
  editForm: any;
  agencies: AgencyDetails[] = [];
  apiUrl = environment.apiUrl

  constructor(
    private modalService: NgbModal,
    private formBuilder: FormBuilder,
    private http: HttpClient
  ) {
  }

  ngOnInit(): void {
    this.http.get<Page>(`${this.apiUrl}/agencies`)
      .subscribe(resp => this.agencies = resp.content)

    this.editForm = this.formBuilder.group({
      uuid: [''],
      name: [''],
      country: [''],
      countryCode: [''],
      city: [''],
      street: [''],
      settlementCurrency: [''],
      contactPerson: ['']
    })
  }

  open(content: any) {
    this.modalService.open(content, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
  }

  openEdit(content: any, currentAgency: AgencyDetails) {
    this.modalService.open(content, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });

    this.editForm.patchValue({
      name: currentAgency.name,
      country: currentAgency.country,
      countryCode: currentAgency.countryCode,
      city: currentAgency.city,
      street: currentAgency.street,
      settlementCurrency: currentAgency.settlementCurrency,
      contactPerson: currentAgency.contactPerson
    })
  }

  onRemove(id: string) {
    // this.http.delete<any>(`${this.apiUrl}/agencies/${id}`)
    //   .pipe(switchMap((v, i) =>  this.http.get<Page>("${this.apiUrl}/agencies")))
    //   .subscribe(resp => this.agencies = resp.content)

    this.http.delete<any>(`${this.apiUrl}/agencies/${id}`)
      .subscribe((resp) => this.ngOnInit())
  }

  newAgencyAdded(event: any) {
    this.ngOnInit()
  }

  updateAgency() {
    this.http.put<any>(`${this.apiUrl}/agencies${this.editForm.value.uuid}`, this.editForm.value)
      .subscribe((resp) => this.ngOnInit())
  }
}

export class AgencyDetails {
  constructor(
    public uuid: string,
    public name: string,
    public country: string,
    public countryCode: string,
    public city: string,
    public street: string,
    public settlementCurrency: string,
    public contactPerson: string) {
  }
}

export class Page{

  constructor(
    public content: AgencyDetails[],
    public totalPages: number,
    public totalElements: number,
    public size: number,
    public number: number,
    public numberOfElements: number
  ) {
  }
}
