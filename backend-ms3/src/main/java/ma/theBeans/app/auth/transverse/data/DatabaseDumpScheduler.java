package ma.theBeans.app.auth.transverse.data;

import org.springframework.stereotype.Component;

@Component
public class DatabaseDumpScheduler {
//    @Value("${spring.datasource.username}")
//    private String dbUsername;
//    @Value("${spring.datasource.password}")
//    private String dbPassword;
//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//    @Autowired
//    private DumpUtil dumpUtil;
//    @Autowired
//    private PushUtil pushUtil;
//    @Value("${remoteRepo.projectName}")
//    private String projectnameInRemoteRepo;
//    @Value("${remoteRepo.dumpFolder}")
//    private String dumpFolder;
//    @Value("${remoteRepo.shFolder}")
//    private String shFolder;
//    @Value("${remoteRepo.commitMessage}")
//    private String commitMessage;
//    @Value("${remoteRepo.username}")
//    private String remoteRepoUsername;
//    @Value("${remoteRepo.accessToken}")
//    private String personalAccessToken;
//
//    @Scheduled(cron = "0 */5 * * * ?")
//    public void dumpDatabase() {
//        dumpUtil.execFile();
//        List<String> errors = pushUtil.createExecFile(dumpFolder, shFolder, projectnameInRemoteRepo, commitMessage, remoteRepoUsername, personalAccessToken);
//        System.out.println("errors = " + errors);
//    }
}
