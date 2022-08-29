import {Component, OnInit} from '@angular/core';
import {AgenciesService, AgencyDetails} from "./agencies.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'agencies',
  templateUrl: './agencies.component.html'
})
export class AgenciesComponent implements OnInit {

  title = "List of agencies"
  editForm: any;
  agencies: AgencyDetails[];

  constructor(
    private service: AgenciesService,
    private modalService: NgbModal,
    private formBuilder: FormBuilder
  ) {
    this.agencies = service.getAgencies()
  }

  ngOnInit(): void {
    this.editForm = this.formBuilder.group({
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
    this.service.remove(id);
    this.agencies = this.service.getAgencies()
  }

  newAgencyAdded(event: any) {
    this.agencies = this.service.getAgencies()
  }

  updateAgency() {
    this.service.put(this.editForm.value)
  }
}
