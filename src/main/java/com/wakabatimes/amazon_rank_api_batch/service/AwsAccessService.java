package com.wakabatimes.amazon_rank_api_batch.service;

import com.wakabatimes.amazon_rank_api_batch.AmazonRankApiBatchApplication;
import com.wakabatimes.amazon_rank_api_batch.entity.AwsAccessKey;
import com.wakabatimes.amazon_rank_api_batch.entity.AwsAccessParameter;
import com.wakabatimes.amazon_rank_api_batch.helper.SignedRequestsHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AwsAccessService {
    private static final Logger logger = LoggerFactory.getLogger(AmazonRankApiBatchApplication.class);

    @Autowired
    private FileParseService fileParseService;

    public String getRequestUrl(String path1, String path2) {
        AwsAccessKey awsAccessKey = fileParseService.getAccessKeyFile(path1);

        /*
         * Set up the signed requests helper.
         */
        SignedRequestsHelper helper = null;
        try {
            helper = SignedRequestsHelper.getInstance(awsAccessKey.getEndpoint(), awsAccessKey.getAccessKeyId(), awsAccessKey.getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        String requestUrl = null;
        AwsAccessParameter awsAccessParameter = fileParseService.getParameterFile(path2);

        Map<String, String> params = new HashMap<>();

        params.put("Service", awsAccessParameter.getService());
        params.put("Operation", awsAccessParameter.getOperation());
        params.put("AWSAccessKeyId", awsAccessParameter.getAwsAccessKeyId());
        params.put("AssociateTag", awsAccessParameter.getAssociateTag());
        params.put("BrowseNodeId", awsAccessParameter.getBrowseNodeId());
        params.put("ResponseGroup", awsAccessParameter.getResponseGroup());
        if (helper != null) {
            requestUrl = helper.sign(params);
        }

        return requestUrl;
    }
}
