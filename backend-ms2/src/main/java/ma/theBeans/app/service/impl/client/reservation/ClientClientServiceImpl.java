package ma.theBeans.app.service.impl.client.reservation;


import ma.theBeans.app.thePackage.exception.EntityNotFoundException;
import ma.theBeans.app.bean.core.reservation.Client;
import ma.theBeans.app.dao.criteria.core.reservation.ClientCriteria;
import ma.theBeans.app.dao.facade.core.reservation.ClientDao;
import ma.theBeans.app.dao.specification.core.reservation.ClientSpecification;
import ma.theBeans.app.service.facade.client.reservation.ClientClientService;

import static ma.theBeans.app.thePackage.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.theBeans.app.thePackage.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import ma.theBeans.app.thePackage.security.service.facade.UserService;
import ma.theBeans.app.thePackage.security.service.facade.RoleService;
import ma.theBeans.app.thePackage.security.service.facade.RoleUserService;
import ma.theBeans.app.thePackage.security.bean.Role;
import ma.theBeans.app.thePackage.security.bean.RoleUser;
import ma.theBeans.app.thePackage.security.common.AuthoritiesConstants;
import ma.theBeans.app.thePackage.security.service.facade.ModelPermissionUserService;

@Service
public class ClientClientServiceImpl implements ClientClientService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Client update(Client t) {
        Client loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Client.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Client findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Client findOrSave(Client t) {
        if (t != null) {
            Client result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Client> findAll() {
        return dao.findAll();
    }

    public List<Client> findByCriteria(ClientCriteria criteria) {
        List<Client> content = null;
        if (criteria != null) {
            ClientSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ClientSpecification constructSpecification(ClientCriteria criteria) {
        ClientSpecification mySpecification =  (ClientSpecification) RefelexivityUtil.constructObjectUsingOneParam(ClientSpecification.class, criteria);
        return mySpecification;
    }

    public List<Client> findPaginatedByCriteria(ClientCriteria criteria, int page, int pageSize, String order, String sortField) {
        ClientSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ClientCriteria criteria) {
        ClientSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Client> delete(List<Client> list) {
		List<Client> result = new ArrayList();
        if (list != null) {
            for (Client t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }


    public Client findWithAssociatedLists(Long id){
        Client result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Client> update(List<Client> ts, boolean createIfNotExist) {
        List<Client> result = new ArrayList<>();
        if (ts != null) {
            for (Client t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Client loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Client t, Client loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Client findByReferenceEntity(Client t){
        return t==null? null : dao.findByEmail(t.getEmail());
    }



    public List<Client> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Client>> getToBeSavedAndToBeDeleted(List<Client> oldList, List<Client> newList) {
        List<List<Client>> result = new ArrayList<>();
        List<Client> resultDelete = new ArrayList<>();
        List<Client> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<Client> oldList, List<Client> newList, List<Client> resultUpdateOrSave, List<Client> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Client myOld = oldList.get(i);
                Client t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Client myNew = newList.get(i);
                Client t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}

    @Override
    public String uploadFile(String checksumOld, String tempUpladedFile, String destinationFilePath) throws Exception {
        return null;
    }


    @Override
    public Client create(Client t) {
        if (findByUsername(t.getUsername()) != null || t.getPassword() == null) return null;
        t.setPassword(userService.cryptPassword(t.getPassword()));
        t.setEnabled(true);
        t.setAccountNonExpired(true);
        t.setAccountNonLocked(true);
        t.setCredentialsNonExpired(true);
        t.setPasswordChanged(false);

        Role role = new Role();
        role.setAuthority(AuthoritiesConstants.CLIENT);
        role.setCreatedAt(LocalDateTime.now());
        Role myRole = roleService.create(role);
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(myRole);
        if (t.getRoleUsers() == null)
            t.setRoleUsers(new ArrayList<>());

        t.getRoleUsers().add(roleUser);
        if (t.getModelPermissionUsers() == null)
            t.setModelPermissionUsers(new ArrayList<>());

        t.setModelPermissionUsers(modelPermissionUserService.initModelPermissionUser());

        Client mySaved = dao.save(t);

        if (t.getModelPermissionUsers() != null) {
            t.getModelPermissionUsers().forEach(e -> {
                e.setUser(mySaved);
                modelPermissionUserService.create(e);
            });
        }
        if (t.getRoleUsers() != null) {
            t.getRoleUsers().forEach(element-> {
                element.setUser(mySaved);
                roleUserService.create(element);
            });
        }

        return mySaved;
     }

    public Client findByUsername(String username){
        return dao.findByUsername(username);
    }

    public boolean changePassword(String username, String newPassword) {
        return userService.changePassword(username, newPassword);
    }




    private @Autowired UserService userService;
    private @Autowired RoleService roleService;
    private @Autowired ModelPermissionUserService modelPermissionUserService;
    private @Autowired RoleUserService roleUserService;


    public ClientClientServiceImpl(ClientDao dao) {
        this.dao = dao;
    }

    private ClientDao dao;
}
