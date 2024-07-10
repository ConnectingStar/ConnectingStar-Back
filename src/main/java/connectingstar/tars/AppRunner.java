package connectingstar.tars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class AppRunner implements ApplicationRunner {

    private final DataSource dataSource;

    public AppRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==== DB connection ====");
        log.info(dataSource.getConnection().getMetaData().getURL());
    }
}
