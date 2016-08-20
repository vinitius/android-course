/*
 *
 *  Copyright (c) 2016 Speakgame
 *  All rights reserved.
 *
 *  This software is a confidential and proprietary information of Speakgame.
 *  ("Confidential Information").  You shall not disclose such
 *  Confidential Information and shall use it only in accordance with the terms
 *  of the license agreement you entered into with Speakgame.
 * /
 *
 */

package br.com.marvel.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vinitius on 8/20/16.
 */
public class ResponseWrapper implements Serializable {

    private String code;
    private String status;
    private DataWrapper data;

    public DataWrapper getData() {
        return data;
    }

    public void setData(DataWrapper data) {
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public static class DataWrapper implements Serializable{
        private List<Character> results;

        public List<Character> getResults() {
            return results;
        }

        public void setResults(List<Character> results) {
            this.results = results;
        }
    }
}
