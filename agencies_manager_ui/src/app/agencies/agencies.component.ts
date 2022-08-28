import {Component} from '@angular/core';
import {AgenciesService, AgencyDetails} from "./agencies.service";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'agencies',
  templateUrl: './agencies.component.html'
})
export class AgenciesComponent {

  title = "List of agencies"
  closeResult: string = '';

  agencies: AgencyDetails[];

  constructor(
    private service: AgenciesService,
    private modalService: NgbModal
  ) {
    this.agencies = service.getAgencies()
  }

  onRemove(id: string) {
    this.service.remove(id);
    this.agencies = this.service.getAgencies()
  }

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      console.log("opened", result)
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {

    console.log("closed");
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onSubmit(form: NgForm) {

    console.log("submit", form.value);
  }

}
