/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.service;

import fatec.bancodedados.model.OtpData;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author isaqu
 */
public class OtpService {
    private static final OtpService INSTANCE = new OtpService();

    private static final long OTP_VALID_DURATION_MINUTES = 10;
    private final Map<String, OtpData> otpCache = new HashMap<>();
    
    public static OtpService getInstance(){
        return INSTANCE;
    }
    
    public String generateOtp(String key) {
        String otp = String.valueOf(100000 + new SecureRandom().nextInt(900000));
        otpCache.put(key, new OtpData(otp, LocalDateTime.now()));
        return otp;
    }
    
    public boolean validateOtp(String key, String otp) {
        OtpData otpData = otpCache.get(key);
        if (otpData == null) {
            return false;
        }
        if (otpData.getCreationTime().plusMinutes(OTP_VALID_DURATION_MINUTES).isBefore(LocalDateTime.now())) {
            otpCache.remove(key);
            return false;
        }
        if (otpData.getOtp().equals(otp)) {
            otpCache.remove(key);
            return true;
        }
        return false;
    }
}
