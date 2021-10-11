package com.example.ds;

import com.example.ds.role.model.ERole;
import com.example.ds.role.model.Role;
import com.example.ds.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        for (ERole value : ERole.values()) {
            roleRepository.save(Role.builder().name(value).build());
        }
    }
}
