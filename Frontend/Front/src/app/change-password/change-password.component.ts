import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, AbstractControl, FormGroup, Validators} from '@angular/forms'
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService, UserService} from '../service';
import {Subject} from 'rxjs/Subject';
import {takeUntil} from 'rxjs/operators';


interface DisplayMessage {
  msgType: string;
  msgBody: string;
}

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  title = 'Change Password'
  form: FormGroup;
  submitted = false;
  notification: DisplayMessage;
  returnUrl: string;
  current: AbstractControl;
  password: AbstractControl;
  confirm: AbstractControl;
  private ngUnsubscribe: Subject<void> = new Subject<void>();


  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder) {
    
   }

  ngOnInit() {
    this.route.params
    .pipe(takeUntil(this.ngUnsubscribe))
    .subscribe((params: DisplayMessage) => {
      this.notification = params;
    });
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.form = this.formBuilder.group({
      username: this.userService.currentUser.username,
      current: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])],
      confirm: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
    }
    );
    this.current = this.form.controls['current'];
    this.password = this.form.controls['password'];
    this.confirm = this.form.controls['confirm'];
  }
  

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  onSubmit() {
    /**
     * Innocent until proven guilty
     */
    this.notification = undefined;
    this.submitted = true;
    this.authService.changePassword(this.form.value)
    .subscribe(data => {
      console.log(data);
      this.authService.login(this.form.value).subscribe(() => {
        this.userService.getMyInfo().subscribe();
      });
      this.router.navigate([this.returnUrl]);
    },
      error => {
        this.submitted = false;
        console.log('Sign up error');
        this.notification = { msgType: 'error', msgBody: error['error'].message };
      });

  }

}
