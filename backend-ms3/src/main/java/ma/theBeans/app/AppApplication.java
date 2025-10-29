package ma.theBeans.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ma.theBeans.app.auth.bean.Client;
import ma.theBeans.app.auth.security.bean.*;
import ma.theBeans.app.auth.security.service.facade.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;


import ma.theBeans.app.auth.security.common.AuthoritiesConstants;

@SpringBootApplication
//@EnableFeignClients
public class AppApplication {
    public static ConfigurableApplicationContext ctx;


    //state: primary success info secondary warning danger contrast
    //_STATE(Pending=warning,Rejeted=danger,Validated=success)
    public static void main(String[] args) {
        ctx=SpringApplication.run(AppApplication.class, args);
    }


    @Bean
    ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ConfigurableApplicationContext getCtx() {
        return ctx;
    }

    @Bean
    public CommandLineRunner demo(UserService userService, RoleService roleService, ModelPermissionService modelPermissionService, ActionPermissionService actionPermissionService, ModelPermissionUserService modelPermissionUserService , ClientAdminService clientService) {
        return (args) -> {
            if(true){


        /*
        List<ModelPermission> modelPermissions = new ArrayList<>();
        addPermission(modelPermissions);
        modelPermissions.forEach(e -> modelPermissionService.create(e));
        List<ActionPermission> actionPermissions = new ArrayList<>();
        addActionPermission(actionPermissions);
        actionPermissions.forEach(e -> actionPermissionService.create(e));
        */

                // User Admin
                User userForAdmin = new User("admin");
                userForAdmin.setPassword("123");
                // Role Admin
                Role roleForAdmin = new Role();
                roleForAdmin.setAuthority(AuthoritiesConstants.ADMIN);
                roleForAdmin.setCreatedAt(LocalDateTime.now());
                Role roleForAdminSaved = roleService.create(roleForAdmin);
                RoleUser roleUserForAdmin = new RoleUser();
                roleUserForAdmin.setRole(roleForAdminSaved);
                if (userForAdmin.getRoleUsers() == null)
                    userForAdmin.setRoleUsers(new ArrayList<>());

                userForAdmin.getRoleUsers().add(roleUserForAdmin);


                userForAdmin.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

                userService.create(userForAdmin);

                // User Client
                Client userForClient = new Client("client");
                userForClient.setPassword("123");
                // Role Client
                Role roleForClient = new Role();
                roleForClient.setAuthority(AuthoritiesConstants.CLIENT);
                roleForClient.setCreatedAt(LocalDateTime.now());
                Role roleForClientSaved = roleService.create(roleForClient);
                RoleUser roleUserForClient = new RoleUser();
                roleUserForClient.setRole(roleForClientSaved);
                if (userForClient.getRoleUsers() == null)
                    userForClient.setRoleUsers(new ArrayList<>());

                userForClient.getRoleUsers().add(roleUserForClient);


                userForClient.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

                clientService.create(userForClient);

            }
        };
    }




    private static String fakeString(String attributeName, int i) {
        return attributeName + i;
    }

    private static Long fakeLong(String attributeName, int i) {
        return  10L * i;
    }
    private static Integer fakeInteger(String attributeName, int i) {
        return  10 * i;
    }

    private static Double fakeDouble(String attributeName, int i) {
        return 10D * i;
    }

    private static BigDecimal fakeBigDecimal(String attributeName, int i) {
        return  BigDecimal.valueOf(i*1L*10);
    }

    private static Boolean fakeBoolean(String attributeName, int i) {
        return i % 2 == 0 ? true : false;
    }
    private static LocalDateTime fakeLocalDateTime(String attributeName, int i) {
        return LocalDateTime.now().plusDays(i);
    }


    private static void addPermission(List<ModelPermission> modelPermissions) {
        modelPermissions.add(new ModelPermission("Book"));
        modelPermissions.add(new ModelPermission("Category"));
        modelPermissions.add(new ModelPermission("Author"));
        modelPermissions.add(new ModelPermission("Copy"));
        modelPermissions.add(new ModelPermission("User"));
        modelPermissions.add(new ModelPermission("ModelPermission"));
        modelPermissions.add(new ModelPermission("ActionPermission"));
    }

    private static void addActionPermission(List<ActionPermission> actionPermissions) {
        actionPermissions.add(new ActionPermission("list"));
        actionPermissions.add(new ActionPermission("create"));
        actionPermissions.add(new ActionPermission("delete"));
        actionPermissions.add(new ActionPermission("edit"));
        actionPermissions.add(new ActionPermission("view"));
        actionPermissions.add(new ActionPermission("duplicate"));
    }


}


