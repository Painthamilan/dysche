package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.Staff;
import com.painthu.dynamictable.Service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
public class StaffController {

    private final StaffService service;

    public StaffController(StaffService service) {
        this.service = service;
    }

    @PostMapping
    public Staff createStaff(@RequestBody Staff staff){
        return service.createStaff(staff);
    }

    @GetMapping
    public List<Staff> getAllStaffs(){
        return service.getAllStaffs();
    }

}
