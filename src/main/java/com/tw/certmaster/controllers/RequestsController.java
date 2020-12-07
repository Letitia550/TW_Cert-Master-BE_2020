package com.tw.certmaster.controllers;


import com.tw.certmaster.exceptions.RequestParamValidationException;
import com.tw.certmaster.helpers.ExcelWriter;
import com.tw.certmaster.models.Request;
import com.tw.certmaster.results.RequestResult;
import com.tw.certmaster.results.RequestResultExcel;
import com.tw.certmaster.services.RequestsServices;
import com.tw.certmaster.services.UsersServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController

public class RequestsController {
    @CrossOrigin("*")
    @GetMapping("/requests")
    public ArrayList<RequestResult> GetRequestsOfUser(@RequestParam Long user_id) throws RequestParamValidationException {
        if( user_id == null ) {
            throw new RequestParamValidationException("The user id cannot be null.");
        }

        if( !UsersServices.userExists(user_id) ) {
            throw new RequestParamValidationException("User with the given id does not exist.");
        }

        ArrayList<RequestResult> requestsOfUser = RequestsServices.getRequestsOfUser(user_id);

        return requestsOfUser;
    }

    @CrossOrigin("*")
    @PostMapping("/requests")
    public RequestResult AddRequest(@RequestBody Request request) {
        Request createdRequest = RequestsServices.createRequest(request);

        return RequestsServices.findRequestResult(createdRequest.getId());
    }

    @CrossOrigin("*")
    @DeleteMapping("/requests/{requestId}")
    public String DeleteRequest(@PathVariable Long requestId) {
        RequestResult requestToBeDeleted = RequestsServices.findRequestResult(requestId);

        if( requestToBeDeleted == null ) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The request with the id you've sent doesn't exist."
            );
        }

        RequestsServices.deleteRequest(requestId);
        return "Request successfully deleted.";
    }

    @CrossOrigin("*")
    @GetMapping("/admin/requests")
    public ArrayList<RequestResult> GetAllRequests(@RequestParam(required = false) Integer quarter,
                                                   @RequestParam(required = false) String status) throws RequestParamValidationException {

        if( quarter != null ) {
            if (quarter < 1 || quarter > 4) {
                throw new RequestParamValidationException("Quarter must be 1, 2, 3 or 4.");
            }
        }

        if( status != null ) {
            if ( !status.equals("Approved") && !status.equals("Pending") && !status.equals("Rejected")) {
                throw new RequestParamValidationException("Status must be 'Approved', 'Pending' or 'Rejected'.");
            }
        }

        return RequestsServices.getAllRequest(quarter, status);
    }

    @CrossOrigin("*")
    @GetMapping(value = "/admin/requests/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public byte[] GetAllRequestsExcel(@RequestParam(required = false) Integer quarter,
                                                             @RequestParam(required = false) String status) throws RequestParamValidationException, IOException {

        if( quarter != null ) {
            if (quarter < 1 || quarter > 4) {
                throw new RequestParamValidationException("Quarter must be 1, 2, 3 or 4.");
            }
        }

        if( status != null ) {
            if ( !status.equals("Approved") && !status.equals("Pending") && !status.equals("Rejected")) {
                throw new RequestParamValidationException("Status must be 'Approved', 'Pending' or 'Rejected'.");
            }
        }

        ArrayList<RequestResultExcel> dataset = RequestsServices.getAllRequestForExcel(quarter, status);

        ExcelWriter writer = new ExcelWriter(dataset, "Requests");

        return writer.getFileToBeReturnedInBytes();
    }


    @CrossOrigin("*")
    @PatchMapping("/requests/{requestId}")
    public RequestResult Editrequest(@RequestBody HashMap<String, String> body, @PathVariable Long requestId) throws RequestParamValidationException {
        if ( requestId == null ) {
            throw new RequestParamValidationException("The request id cannot be null.");
        }

        if (body.get("business_justification") != null) {
            if (body.get("business_justification").length() < 10) {
                throw  new RequestParamValidationException("Enter at least 10 characters for business justification.");
            }
        }

        return RequestsServices.updateRequest(body.get("business_justification"), requestId);
    }

    @CrossOrigin("*")
    @PatchMapping("/admin/requests/{requestId}")
    public RequestResult EditRequest(@RequestBody HashMap<String, String> body, @PathVariable Long requestId) throws RequestParamValidationException{
        if ( requestId == null ) {
            throw new RequestParamValidationException("The request id cannot be null.");
        }

        if( body.get("status") != null ) {
            if ( !body.get("status").equals("Approved") && !body.get("status").equals("Pending") && !body.get("status").equals("Rejected")) {
                throw new RequestParamValidationException("Status must be 'Approved', 'Pending' or 'Rejected'.");
            }
        }

        return RequestsServices.editRequest(body.get("status"), requestId);
    }

    @CrossOrigin("*")
    @PatchMapping("/admin/requests/mass-approve")
    public ArrayList<RequestResult> MassAproveRequests (@RequestBody HashMap<String, String> body) throws RequestParamValidationException {
        if( body.get("quarter") == null ) {
            throw new RequestParamValidationException("The quarter cannot be null.");
        }

        if( body.get("user_id") == null ) {
            throw new RequestParamValidationException("The user id cannot be null.");
        }

        if ( body.get("quarter") != null ) {
            if ( Integer.parseInt(body.get("quarter")) < 1 || Integer.parseInt(body.get("quarter")) > 4) {
                throw new RequestParamValidationException("Quarter must be 1, 2, 3 or 4.");
            }

            if( !UsersServices.userExists(Integer.parseInt(body.get("user_id")))) {
                throw new RequestParamValidationException("User with the given id does not exist.");
            }
        }
        return RequestsServices.massAproveRequests(Integer.parseInt(body.get("quarter")), Integer.parseInt(body.get("user_id")));
    }
}
