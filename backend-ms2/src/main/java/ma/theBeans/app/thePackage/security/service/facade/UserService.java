package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.dao.criteria.core.UserCriteria;
import ma.theBeans.app.thePackage.security.bean.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ma.theBeans.app.thePackage.service.IService;

public interface UserService extends IService<User, UserCriteria>, UserDetailsService {

    User findByUsername(String username);

    User findByEmail(String email);

    User findByLinkValidationCode(String linkValidationCode);

    User findByUsernameWithRoles(String username);

    String cryptPassword(String value);

    boolean changePassword(String username, String newPassword);

    int deleteByUsername(String username);

    UserDetails loadUserByUsername(String username);

    String generateCode(int length);

    User createAndDisable(User t);


}
