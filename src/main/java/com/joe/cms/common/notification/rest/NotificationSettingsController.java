package com.joe.cms.common.notification.rest;

import com.joe.cms.common.notification.dto.EmailSettingsDTO;
import com.joe.cms.common.notification.dto.SmsSettingsDTO;
import com.joe.cms.common.notification.service.NotificationService;
import com.joe.cms.parcel.dto.ParcelDTO;
import com.joe.cms.parcel.dto.WeightBandDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/v1/company/{companyId}/notification")
public class NotificationSettingsController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationSettingsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/listEmail")
    public ResponseEntity<?> getEmailSettings(@PathVariable Long companyId) {
        try {
            List<EmailSettingsDTO> emailSettings = notificationService.getEmailSettings(companyId);
            return ResponseEntity.ok(emailSettings);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving email setup: " + e.getMessage());
        }
    }

    @PostMapping("/createEmail")
    public ResponseEntity<?> createEmailSettings(@PathVariable Long companyId, @RequestBody EmailSettingsDTO emailSettingsDTO) {

        Map<String, String> response = new HashMap<>();
        try {
            EmailSettingsDTO createdEmailSetting = notificationService.saveEmailSettings(companyId, emailSettingsDTO);
            response.put("message", "Saved Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
            // return ResponseEntity.status(201).body(createdWeightBand);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid Input.." + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            // return ResponseEntity.status(400).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            response.put("message", "Error creating email config:" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(500).body("Error creating weight band: " + e.getMessage());
        }
    }

    @GetMapping("/sms-settings/{companyId}")
    public ResponseEntity<?> getSmsSettings(@PathVariable Long companyId) {
        SmsSettingsDTO smsSettingsDTO = notificationService.getSmsSettings(companyId);
        return ResponseEntity.ok(smsSettingsDTO);
    }

    @PostMapping("/sms-settings")
    public ResponseEntity<?> saveSmsSettings(@RequestBody SmsSettingsDTO smsSettingsDTO) {
        notificationService.saveSmsSettings(smsSettingsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
