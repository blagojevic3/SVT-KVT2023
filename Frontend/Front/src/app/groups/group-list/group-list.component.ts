import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupService } from 'src/app/service';
import { Subject } from 'rxjs/Subject';
import { takeUntil } from 'rxjs/operators';

interface DisplayMessage {
  msgType: string;
  msgBody: string;
}


@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {
  @Input() groups: any[]
  editing = false;
  form: FormGroup
  private ngUnsubscribe: Subject<void> = new Subject<void>();
  notification: DisplayMessage;
  returnUrl: string;



  constructor(
    private groupService: GroupService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
  ) {}

  ngOnInit() {}

  deleteGroup(groupId: number) {
    console.log(groupId)
    
    this.groupService.delete(groupId).subscribe((groups) => {this.groupService.getGroups()});
  }
  editGroup(groupId, groupName, groupDesc) {
    this.editing = true;
    this.route.params
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe((params: DisplayMessage) => {
        this.notification = params;
      });
    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.form = this.formBuilder.group({
      id: groupId,
      name: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
      description: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
    });
    this.form.get("name").setValue(groupName)
    this.form.get('description').setValue(groupDesc)
  }

  onSubmit(){
    this.groupService.edit(this.form.value).subscribe((result) => {
    });
  }


}
