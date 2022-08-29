import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForm} from "@angular/forms";
import {AgenciesService} from "../agencies/agencies.service";

@Component({
  selector: 'create-agency-form',
  templateUrl: './create-agency-form.component.html',
  styleUrls: ['./create-agency-form.component.css']
})
export class CreateAgencyFormComponent {
  @Input() modal: any;
  @Output() newItemEvent = new EventEmitter<string>();

  constructor(private service: AgenciesService) { }

  submit(f:NgForm) {
    this.service.add(f.value);
    this.newItemEvent.emit("new item added");
  }
}
