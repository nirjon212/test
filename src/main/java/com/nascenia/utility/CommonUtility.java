package com.nascenia.utility;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mozammal on 6/13/17.
 */

@Component
public class CommonUtility {

    public StringBuffer retrieveRestApiResponseText(HttpResponse rawResponse) throws IOException {

        BufferedReader rd =
                new BufferedReader(new InputStreamReader(rawResponse.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result;
    }
}
