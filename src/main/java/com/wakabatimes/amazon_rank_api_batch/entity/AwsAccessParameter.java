package com.wakabatimes.amazon_rank_api_batch.entity;

import lombok.Data;

@Data
public class AwsAccessParameter {
    private String service;
    private String operation;
    private String awsAccessKeyId;
    private String associateTag;
    private String browseNodeId;
    private String responseGroup;
}
