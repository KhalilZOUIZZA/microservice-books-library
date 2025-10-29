package ma.theBeans.app.auth.security.service.facade;

import ma.theBeans.app.auth.bean.Client;
import ma.theBeans.app.auth.security.dao.criteria.core.ClientCriteria;

import java.util.List;


public interface ClientAdminService {


    Client findByUsername(String username);
    boolean changePassword(String username, String newPassword);





    Client create(Client t);

    Client update(Client t);

    List<Client> update(List<Client> ts,boolean createIfNotExist);

    Client findById(Long id);

    Client findOrSave(Client t);

    Client findByReferenceEntity(Client t);

    Client findWithAssociatedLists(Long id);

    List<Client> findAllOptimized();

    List<Client> findAll();

    List<Client> findByCriteria(ClientCriteria criteria);

    List<Client> findPaginatedByCriteria(ClientCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ClientCriteria criteria);

    List<Client> delete(List<Client> ts);

    boolean deleteById(Long id);

    List<List<Client>> getToBeSavedAndToBeDeleted(List<Client> oldList, List<Client> newList);

    public String uploadFile(String checksumOld, String tempUpladedFile,String destinationFilePath) throws Exception ;

}