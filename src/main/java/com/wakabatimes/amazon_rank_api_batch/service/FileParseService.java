package com.wakabatimes.amazon_rank_api_batch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakabatimes.amazon_rank_api_batch.AmazonRankApiBatchApplication;
import com.wakabatimes.amazon_rank_api_batch.entity.AwsAccessKey;
import com.wakabatimes.amazon_rank_api_batch.entity.AwsAccessParameter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class FileParseService {
    private static final Logger logger = LoggerFactory.getLogger(AmazonRankApiBatchApplication.class);
    public AwsAccessKey getAccessKeyFile(String path) {
        String data = null;
        try {
            data = readFile(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        AwsAccessKey awsAccessKey = null;
        try {
            awsAccessKey = mapper.readValue(data, AwsAccessKey.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return awsAccessKey;
    }

    public AwsAccessParameter getParameterFile(String path) {
        String data = null;
        try {
            data = readFile(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        ObjectMapper mapper = new ObjectMapper();
        AwsAccessParameter awsAccessParameter = null;
        try {
            awsAccessParameter = mapper.readValue(data, AwsAccessParameter.class);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return awsAccessParameter;
    }

    private String readFile(String path) {
        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        StringBuilder data = new StringBuilder();
        String str = null;
        try {
            if (br != null) {
                str = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        while(str != null){
            data.append(str);
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return data.toString();
    }


}
