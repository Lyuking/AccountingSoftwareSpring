package ru.pin120.luka.AccountingSoftware.Repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.pin120.luka.AccountingSoftware.Models.Audience;

import java.util.List;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    List<Audience> findByNameIgnoreCase(String name);
//    private List<Audience> audiences;
//    public List<Audience> getAudiences() {
//        return audiences;
//    }
//    @PostConstruct
//    public void init() {
//        audiences = new ArrayList<>();
//        audiences.add(new Audience(1l, "404-1"));
//        audiences.add(new Audience(2l, "405-1"));
//        audiences.add(new Audience(3l, "406-1"));
//        audiences.add(new Audience(3l, "407-1"));
//    }
//    public void deleteById(Long id) {
//        for (int i = 0; i < audiences.size(); i++) {
//            if (audiences.get(i).getId().equals(id)) {
//                audiences.remove(i);
//                return;
//            }
//        }
//    }
//
//    public void addComputer(Long id, Computer computer) {
//        audiences.get(id.intValue()).getComputers().add(computer);
//        computer.setAudience(audiences.get(id.intValue()));
//    }
//
////    public void removeComputer(Long id, Computer computer) {
////        audiences.get(i).getComputers().get(c)computers.remove(computer);
////        computer.setAudience(null);
////    }
}