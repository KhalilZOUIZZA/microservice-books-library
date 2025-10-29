package ma.theBeans.app.thePackage.security.service.impl;


import ma.theBeans.app.thePackage.security.bean.ModelPermissionUser;
import ma.theBeans.app.thePackage.security.bean.RoleUser;
import ma.theBeans.app.thePackage.security.bean.User;
import ma.theBeans.app.thePackage.security.dao.criteria.core.UserCriteria;
import ma.theBeans.app.thePackage.security.dao.facade.core.UserDao;
import ma.theBeans.app.thePackage.security.dao.specification.core.UserSpecification;
import ma.theBeans.app.thePackage.security.service.facade.*;
import ma.theBeans.app.thePackage.service.AbstractServiceImpl;
import ma.theBeans.app.thePackage.util.ListUtil;
import ma.theBeans.app.thePackage.util.StringUtil;

import ma.theBeans.app.thePackage.security.common.AuthoritiesConstants;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.BeanUtils;

import ma.theBeans.app.service.facade.admin.reservation.ClientAdminService;
import ma.theBeans.app.bean.core.reservation.Client;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserCriteria, UserDao> implements UserService {



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public User create(User t) {
        return createWithEnable(t, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public User createAndDisable(User t) {
        return createWithEnable(t, false);
    }

    private User createWithEnable(User t, boolean enable) {
        User foundedUserByUsername = findByUsername(t.getUsername());
        User foundedUserByEmail = dao.findByEmail(t.getEmail());
        if (foundedUserByUsername != null || foundedUserByEmail != null) return null;
        else {
            if (t.getPassword() == null || t.getPassword().isEmpty()) {
                t.setPassword(bCryptPasswordEncoder.encode(t.getUsername()));
            } else {
                t.setPassword(bCryptPasswordEncoder.encode(t.getPassword()));
            }
            t.setAccountNonExpired(true);
            t.setAccountNonLocked(true);
            t.setCredentialsNonExpired(true);
            t.setEnabled(true); // change it to enabled khalil
            t.setPasswordChanged(false);
            t.setCreatedAt(LocalDateTime.now());
            /*if (t.getModelPermissionUsers() == null)
                t.setModelPermissionUsers(new ArrayList<>());

            t.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());
            */
            User saved = new User();
            String roleAsString = t.getRoleUsers().get(0).getRole().getAuthority();
			
						
            if (roleAsString.equals(AuthoritiesConstants.ADMIN)) {
                saved = super.create(t);
            } 	 
 
 
 
			else if(roleAsString.equals(AuthoritiesConstants.CLIENT)){
				saved = clientService.create(convertToClient(t));
			}
 
 
 
 
			
		
            /*if (t.getModelPermissionUsers() != null) {
                User finalSaved = saved;
                t.getModelPermissionUsers().forEach(e -> {
                    e.setUser(finalSaved);
                    modelPermissionUserService.create(e);
                });
            }*/
            if (t.getRoleUsers() != null) {
                User finalSaved = saved;
                t.getRoleUsers().forEach(element -> {
                    if (element.getRole() != null && element.getRole().getId() == null && StringUtil.isNotEmpty(element.getRole().getAuthority())) {
                        element.setRole(roleService.findByAuthority(element.getRole().getAuthority()));
                    }
                    element.setUser(finalSaved);
                    roleUserService.create(element);
                });
            }
            return saved;
        }

    }

	 private Client convertToClient(User user) {
        Client item = new Client();
        BeanUtils.copyProperties(user, item);
        return item;
    }


    public User findWithAssociatedLists(Long id) {
        User result = dao.findById(id).orElse(null);
        if (result != null && result.getId() != null) {
            result.setModelPermissionUsers(modelPermissionUserService.findByUserId(id));
            result.setRoleUsers(roleUserService.findByUserId(id));
        }
        return result;
    }

    @Transactional
    public void deleteAssociatedLists(Long id) {
        modelPermissionUserService.deleteByUserId(id);
        roleUserService.deleteByUserId(id);
    }


    public void updateWithAssociatedLists(User user) {
        if (user != null && user.getId() != null) {
            List<List<ModelPermissionUser>> resultModelPermissionUsers = modelPermissionUserService.getToBeSavedAndToBeDeleted(modelPermissionUserService.findByUserId(user.getId()), user.getModelPermissionUsers());
            modelPermissionUserService.delete(resultModelPermissionUsers.get(1));
            ListUtil.emptyIfNull(resultModelPermissionUsers.get(0)).forEach(e -> e.setUser(user));
            modelPermissionUserService.update(resultModelPermissionUsers.get(0), true);
            List<List<RoleUser>> resultRoleUsers = roleUserService.getToBeSavedAndToBeDeleted(roleUserService.findByUserId(user.getId()), user.getRoleUsers());
            roleUserService.delete(resultRoleUsers.get(1));
            ListUtil.emptyIfNull(resultRoleUsers.get(0)).forEach(e -> e.setUser(user));
            roleUserService.update(resultRoleUsers.get(0), true);
        }
    }


    public User findByReferenceEntity(User t) {
        return dao.findByEmail(t.getEmail());
    }

    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }


    public User findByLinkValidationCode(String linkValidationCode) {
        return dao.findByLinkValidationCode(linkValidationCode);
    }

    @Override
    public User findByUsername(String username) {
        if (username == null)
            return null;
        return dao.findByUsername(username);
    }

    public List<User> findAllOptimized() {
        return dao.findAllOptimized();
    }

    public String generateCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public String cryptPassword(String value) {
        return value == null ? null : bCryptPasswordEncoder.encode(value);
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        User user = dao.findByUsername(username);
        if (user != null) {
            user.setPassword(cryptPassword(newPassword));
            user.setPasswordChanged(true);
            dao.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByUsernameWithRoles(String username) {
        if (username == null)
            return null;
        return dao.findByUsername(username);
    }

    @Override
    @Transactional
    public int deleteByUsername(String username) {
        return dao.deleteByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsernameWithRoles(username);
    }

    public void configure() {
        super.configure(User.class, UserSpecification.class);
    }


    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private ModelPermissionService modelPermissionService;
    @Autowired
    private ActionPermissionService actionPermissionService;
    @Autowired
    private ModelPermissionUserService modelPermissionUserService;
    @Autowired
    private RoleService roleService;
    @Lazy
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


    private static final String CHARACTERS = "0123456789";
    @Autowired
    private ClientAdminService clientService;


            public UserServiceImpl(UserDao dao) {
        super(dao);
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User update(User updatedUser) {
        // Fetch the existing user
        User existingUser = dao.findById(updatedUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Update basic fields
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setEnabled(updatedUser.getEnabled());
        existingUser.setAccountNonExpired(updatedUser.getAccountNonExpired());
        existingUser.setAccountNonLocked(updatedUser.getAccountNonLocked());
        existingUser.setCredentialsNonExpired(updatedUser.getCredentialsNonExpired());
        existingUser.setPasswordChanged(updatedUser.getPasswordChanged());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setAvatar(updatedUser.getAvatar());
        existingUser.setAbout(updatedUser.getAbout());

        // Update RoleUsers
        if (updatedUser.getRoleUsers() != null) {
            // Clear existing roles
            roleUserService.deleteByUserId(existingUser.getId());

            // Add new roles
            updatedUser.getRoleUsers().forEach(roleUser -> {
                roleUser.setUser(existingUser);
                roleUserService.create(roleUser);
            });
        }

        // Update ModelPermissionUsers (if needed)
        if (updatedUser.getModelPermissionUsers() != null) {
            modelPermissionUserService.deleteByUserId(existingUser.getId());
            updatedUser.getModelPermissionUsers().forEach(modelPermissionUser -> {
                modelPermissionUser.setUser(existingUser);
                modelPermissionUserService.create(modelPermissionUser);
            });
        }

        // Save updated user
        return dao.save(existingUser);
    }

}
