package ma.theBeans.app.auth.security.service.facade;

public interface LoginAttemptService {
    void loginFailed(String username);

    void loginSucceeded(String username);

    boolean isLocked(String username);

    Long getRemainingLockoutTime(String username);
}
