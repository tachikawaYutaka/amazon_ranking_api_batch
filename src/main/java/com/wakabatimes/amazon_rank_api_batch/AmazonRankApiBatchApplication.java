package com.wakabatimes.amazon_rank_api_batch;

import com.wakabatimes.amazon_rank_api_batch.service.AwsAccessService;
import com.wakabatimes.amazon_rank_api_batch.service.FileSaveService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class AmazonRankApiBatchApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AmazonRankApiBatchApplication.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment env;

    @Autowired
    private AwsAccessService awsAccessService;

    @Autowired
    private FileSaveService fileSaveService;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AmazonRankApiBatchApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = application.run(args);
        SpringApplication.exit(context);
    }

    @Override
    public void run(String... args) {
        logger.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        logger.info("the start of amazon rank api batch");
        List<String> argsList = Arrays.asList(args);
        String requestUrl = null;
        if (argsList.size() == 4) {
            String path1 = argsList.get(0);
            String path2 = argsList.get(1);
            try {
                requestUrl = awsAccessService.getRequestUrl(path1, path2);
                logger.info(requestUrl);
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("Error Incorrect parameters");
        }

        if (requestUrl != null) {
            String dir = argsList.get(2);
            String fileName = argsList.get(3);
            fileSaveService.saveJson(requestUrl,dir, fileName);
        }

        logger.info("the end of amazon rank api batch ");
    }
}
