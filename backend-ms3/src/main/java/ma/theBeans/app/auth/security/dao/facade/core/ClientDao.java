package ma.theBeans.app.auth.security.dao.facade.core;

import ma.theBeans.app.auth.bean.Client;
import ma.theBeans.app.auth.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientDao extends AbstractRepository<Client,Long> {
    Client findByEmail(String email);
    int deleteByEmail(String email);

    Client findByUsername(String username);

    @Query("SELECT NEW Client(item.id,item.email) FROM Client item")
    List<Client> findAllOptimized();

}
