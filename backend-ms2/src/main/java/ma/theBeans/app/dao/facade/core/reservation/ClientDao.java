package ma.theBeans.app.dao.facade.core.reservation;

import org.springframework.data.jpa.repository.Query;
import ma.theBeans.app.thePackage.repository.AbstractRepository;
import ma.theBeans.app.bean.core.reservation.Client;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientDao extends AbstractRepository<Client,Long>  {
    Client findByEmail(String email);
    int deleteByEmail(String email);

    Client findByUsername(String username);

    @Query("SELECT NEW Client(item.id,item.email) FROM Client item")
    List<Client> findAllOptimized();

}
