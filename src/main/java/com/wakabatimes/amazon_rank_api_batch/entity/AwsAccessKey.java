package com.wakabatimes.amazon_rank_api_batch.entity;

import lombok.Data;

@Data
public class AwsAccessKey {
    private String accessKeyId;
    private String secretKey;
    private String endpoint;
}
