import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { GroupService } from 'src/app/service';

@Component({
  selector: 'app-group-list-content',
  templateUrl: './group-list-content.component.html',
  styleUrls: ['./group-list-content.component.css']
})
export class GroupListContentComponent implements OnInit {
  groupList: any[]

  constructor(
    private groupService:GroupService,
    private router:Router
  ) { }

  ngOnInit() {
    this.getGroups()
    console.log(this.groupList)   
  }

  getGroups(){
    this.groupService.getGroups().subscribe((groups) => (this.groupList= groups))
  }
  navigateTo(){
    this.router.navigate(['groups/create'])
  }

}
