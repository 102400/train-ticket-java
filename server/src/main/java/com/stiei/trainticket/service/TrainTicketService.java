package com.stiei.trainticket.service;

import com.stiei.trainticket.bean.AjaxResult;
import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.json.InfoJson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class TrainTicketService {

    public AjaxResult info(InfoJson param) {

        String startLocation = param.getStartLocation();
        String endLocation = param.getEndLocation();

        double price = 0;
        if (!startLocation.equals(endLocation)) {
            price = new Random(startLocation.hashCode()).nextDouble() * 500 + new Random(endLocation.hashCode()).nextDouble() * 500;
        }

        return AjaxResult.newSuccess(Map.of(
                "price", price
        ));
    }

}
