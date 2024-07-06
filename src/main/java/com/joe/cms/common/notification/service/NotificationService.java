package com.joe.cms.common.notification.service;

import com.joe.cms.common.notification.dto.EmailSettingsDTO;
import com.joe.cms.common.notification.dto.SmsSettingsDTO;
import com.joe.cms.common.notification.model.CompanyEmailSettings;
import com.joe.cms.common.notification.model.CompanySmsSettings;
import com.joe.cms.common.notification.repo.CompanyEmailSettingsRepository;
import com.joe.cms.common.notification.repo.SmsSettingsRepository;
import com.joe.cms.company.model.Branch;
import com.joe.cms.company.repo.CompanyRepo;
import com.joe.cms.parcel.dto.WeightBandDTO;
import com.joe.cms.parcel.model.Parcel;
import com.joe.cms.parcel.model.WeightBand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final CompanyRepo companyRepo;

    @Autowired
    private final CompanyEmailSettingsRepository emailSettingsRepository;

    private  final SmsSettingsRepository smsSettingsRepository;

    @Autowired
    private RestTemplate restTemplate;

    public NotificationService(CompanyRepo companyRepo, CompanyEmailSettingsRepository emailSettingsRepository, SmsSettingsRepository smsSettingsRepository) {
        this.companyRepo = companyRepo;
        this.emailSettingsRepository = emailSettingsRepository;
        this.smsSettingsRepository = smsSettingsRepository;
    }


    public void sendNotification(Parcel parcel){

        //Fetch company specific settings

        CompanyEmailSettings emailSettings = emailSettingsRepository.findByCompanyId(parcel.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Email settings not found for company ID: " + parcel.getCompanyId()));

        CompanySmsSettings smsSettings = smsSettingsRepository.findByCompanyId(parcel.getCompanyId())
                .orElseThrow(() -> new RuntimeException("SMS settings not found for company ID: " + parcel.getCompanyId()));


        // Send SMS and Email notifications to the sender and recipient.
        // Implement actual sending logic using an SMS/email API.
        String message = String.format("Your parcel with tracking number %s has been recorded", parcel.getTrackingReference());
        sendSms(parcel.getFromPhone(), message, smsSettings);
        sendSms(parcel.getToPhone(), message, smsSettings);
        sendEmail(parcel.getFromEmail(), message, emailSettings);
        sendEmail(parcel.getToEmail(), message, emailSettings);
    }

    private void sendEmail(String email, String message, CompanyEmailSettings settings) {
        // Implement Email sending logic
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settings.getHost());
        mailSender.setPort(settings.getPort().intValue());
        mailSender.setUsername(settings.getUsername());
        mailSender.setPassword(settings.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Parcel Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    private void sendSms(String phoneNumber, String message, CompanySmsSettings settings) {
        // Example using a hypothetical SMS API
        String smsApiUrl = settings.getApiUrl();
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message, settings.getApiKey());
        restTemplate.postForObject(smsApiUrl, smsRequest, Void.class);
    }

    private static class SmsRequest {
        private String phoneNumber;
        private String message;
        private String apiKey;

        public SmsRequest(String phoneNumber, String message, String apiKey) {
            this.phoneNumber = phoneNumber;
            this.message = message;
            this.apiKey = apiKey;
        }

        // Getters and setters...
    }

    /*public EmailSettingsDTO getEmailSettingss(Long companyId) {
        Optional<CompanyEmailSettings> emailSettingsOptional = emailSettingsRepository.findByCompanyId(companyId);
        if (emailSettingsOptional.isPresent()) {
            CompanyEmailSettings emailSettings = emailSettingsOptional.get();
            return convertToDto(emailSettings);
        } else {
            throw new RuntimeException("Email settings not found for company ID: " + companyId);
        }
    }*/

    public List<EmailSettingsDTO> getEmailSettings(Long companyId) {
        return emailSettingsRepository.findByCompanyId(companyId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EmailSettingsDTO saveEmailSettings(Long companyId, EmailSettingsDTO emailSettingsDTO) {
        Optional<CompanyEmailSettings> existingSettings = emailSettingsRepository.findByCompanyId(companyId);

        if (existingSettings.isPresent()) {
            throw new IllegalArgumentException("Email settings already exist for this company.");
        }

        CompanyEmailSettings emailSettings = convertToEntity(emailSettingsDTO);
        emailSettings.setCompany(companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found")));

        CompanyEmailSettings savedEmailSettings = emailSettingsRepository.save(emailSettings);
        return convertToDto(savedEmailSettings);
    }


    public SmsSettingsDTO getSmsSettings(Long companyId) {
        Optional<CompanySmsSettings> smsSettingsOptional = smsSettingsRepository.findByCompanyId(companyId);
        if (smsSettingsOptional.isPresent()) {
            CompanySmsSettings smsSettings = smsSettingsOptional.get();
            return convertToDto(smsSettings);
        } else {
            throw new RuntimeException("SMS settings not found for company ID: " + companyId);
        }
    }

    public void saveSmsSettings(SmsSettingsDTO smsSettingsDTO) {
        CompanySmsSettings smsSettings = convertToEntity(smsSettingsDTO);
        smsSettingsRepository.save(smsSettings);
    }

    private EmailSettingsDTO convertToDto(CompanyEmailSettings emailSettings) {
        EmailSettingsDTO dto = new EmailSettingsDTO();
        dto.setCompanyId(emailSettings.getCompany().getId());
        dto.setHost(emailSettings.getHost());
        dto.setPort(emailSettings.getPort());
        dto.setUsername(emailSettings.getUsername());
        dto.setPassword(emailSettings.getPassword());
        return dto;
    }

    private CompanyEmailSettings convertToEntity(EmailSettingsDTO dto) {
        CompanyEmailSettings emailSettings = new CompanyEmailSettings();
        // Assuming companyId is set elsewhere or fetched from dto if needed
        emailSettings.setHost(dto.getHost());
        emailSettings.setPort(dto.getPort());
        emailSettings.setUsername(dto.getUsername());
        emailSettings.setPassword(dto.getPassword());
        return emailSettings;
    }

    private SmsSettingsDTO convertToDto(CompanySmsSettings smsSettings) {
        SmsSettingsDTO dto = new SmsSettingsDTO();
        dto.setCompanyId(smsSettings.getCompany().getId());
        dto.setApiUrl(smsSettings.getApiUrl());
        dto.setApiKey(smsSettings.getApiKey());
        return dto;
    }

    private CompanySmsSettings convertToEntity(SmsSettingsDTO dto) {
        CompanySmsSettings smsSettings = new CompanySmsSettings();
        // Assuming companyId is set elsewhere or fetched from dto if needed
        smsSettings.setApiUrl(dto.getApiUrl());
        smsSettings.setApiKey(dto.getApiKey());
        return smsSettings;
    }
}
