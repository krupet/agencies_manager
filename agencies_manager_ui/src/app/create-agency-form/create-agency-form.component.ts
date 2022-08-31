import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForm} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";


@Component({
  selector: 'create-agency-form',
  templateUrl: './create-agency-form.component.html',
  styleUrls: ['./create-agency-form.component.css']
})
export class CreateAgencyFormComponent {
  @Input() modal: any;
  @Output() newItemEvent = new EventEmitter<string>();

  apiUrl = environment.apiUrl

  constructor(private http: HttpClient) { }

  submit(f:NgForm) {
    this.http.post<any>(`${this.apiUrl}/agencies`, f.value)
      .subscribe((resp) => this.newItemEvent.emit("new item added"));
  }
}
