package com.stiei.trainticket.controller;

import com.stiei.trainticket.annotation.Token;
import com.stiei.trainticket.bean.AjaxResult;
import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.json.InfoJson;
import com.stiei.trainticket.service.TrainTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/trainTicket")
public class TrainTicketController {

    @Autowired private TrainTicketService trainTicketService;

    @Token(false)
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public AjaxResult info(@RequestBody InfoJson param) {
        return trainTicketService.info(param);
    }


}
