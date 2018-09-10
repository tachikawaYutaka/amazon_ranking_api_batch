package com.wakabatimes.amazon_rank_api_batch.service;

import com.wakabatimes.amazon_rank_api_batch.AmazonRankApiBatchApplication;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class FileSaveService {
    private static final Logger logger = LoggerFactory.getLogger(AmazonRankApiBatchApplication.class);
    public void saveJson(String requestUrl, String dir, String fileName) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder responseXml = new StringBuilder();
        try{
            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            String charSet = "UTF-8";
            String method = "GET";
            httpURLConnection.setRequestMethod( method );
            InputStreamReader inputStreamReader = new InputStreamReader( httpURLConnection.getInputStream(), charSet );
            bufferedReader = new BufferedReader( inputStreamReader );

            String oneLine;
            while( true ){
                oneLine = bufferedReader.readLine();
                if(oneLine == null){
                    break;
                }else{
                    responseXml.append(oneLine);
                }
            }
        }catch( Exception e ){
            logger.error(e.getMessage());
        }finally{
            if( bufferedReader != null ){
                try{
                    bufferedReader.close();
                }catch( IOException e ){
                    logger.error(e.getMessage());
                }
            }
            if( httpURLConnection != null ){
                httpURLConnection.disconnect();
            }
        }

        //jsonデータに変換
        JSONObject jsonData = XML.toJSONObject(responseXml.toString());


        //ファイル作成
        String filePath = dir + File.separator + fileName;
        File newFile = new File(filePath);

        if(checkBeforeWriteFile(newFile)) {
            // 文字コードを指定する
            PrintWriter p_writer = null;
            try {
                p_writer = new PrintWriter(new BufferedWriter
                        (new OutputStreamWriter(new FileOutputStream(newFile),"UTF-8")));
            } catch (UnsupportedEncodingException | FileNotFoundException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

            String browseNodeLookupResponse = jsonData.getJSONObject("BrowseNodeLookupResponse").getJSONObject("BrowseNodes").toString();
            if (p_writer != null) {
                p_writer.println(browseNodeLookupResponse);
                p_writer.close();
            }
            logger.info("Create " + filePath);
        }else {
            logger.error("Error Can not write the file");
        }
    }

    private static boolean checkBeforeWriteFile(File file) {
        return file.exists() && !(!file.isFile() || !file.canWrite());
    }
}
