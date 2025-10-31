package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Staff;
import com.painthu.dynamictable.Repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository repository;

    public StaffService(StaffRepository repository) {
        this.repository = repository;
    }

    public Staff createStaff(Staff staff) {
        return repository.save(staff);
    }

    public List<Staff> getAllStaffs(){
        return repository.findAll();
    }


    public Staff updateStaff(String id, Staff staffDetails){
        return repository.findById(id)
                .map(staff -> {
                    staff.setName(staffDetails.getName());
                    staff.setModules(staffDetails.getModules());
                    staff.setPreferences(staffDetails.getPreferences());
                    staff.setRole(staffDetails.getRole());
                    staff.setDepartment(staffDetails.getDepartment());
                    return repository.save(staff);
                })
                .orElse(null);
    }

    public void deleteStaff(String id) {
        repository.deleteById(id);
    }
}
