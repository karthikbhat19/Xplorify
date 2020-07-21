package com.example.xplorify.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import java.net.ConnectException;


public class HttpParse {
    String FinalHttpData = "";
    String Result ;
    BufferedWriter bufferedWriter ;
    OutputStream outputStream ;
    BufferedReader bufferedReader ;
    StringBuilder stringBuilder = new StringBuilder();
    URL url;

        public String postRequest(HashMap<String, String>Data, String HttpUrlHolder){

            try{
                url = new URL(HttpUrlHolder);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(14000);
                httpURLConnection.setConnectTimeout(14000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                bufferedWriter.write(FinalDataParse(Data));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                if(httpURLConnection.getResponseCode() == 200){

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    FinalHttpData = bufferedReader.readLine();
                } else{
                    FinalHttpData = "Something Went Grotesquely Wrong";
                }
            }catch (ConnectException CE){
                FinalHttpData = "Please check the ngrok subdomain Address and your network connection";
            }catch (SocketTimeoutException STE){
                FinalHttpData = "Socket Connection Timed out. Seems we can't reach the server";
            }catch (Exception E){
                E.printStackTrace();
                FinalHttpData = E.getMessage();
            }

            return FinalHttpData;
        }




        public String FinalDataParse(HashMap<String,String> dataHashMap) throws UnsupportedEncodingException{
            stringBuilder.delete(0,stringBuilder.length());
            for(Map.Entry<String,String> map_entry : dataHashMap.entrySet()){

                stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(map_entry.getKey(),"UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(map_entry.getValue(),"UTF-8"));
            }

            Result = stringBuilder.toString();

            return Result;
        }
}
